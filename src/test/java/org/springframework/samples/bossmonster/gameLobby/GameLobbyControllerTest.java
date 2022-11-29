package org.springframework.samples.bossmonster.gameLobby;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.configuration.SecurityConfiguration;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = GameLobbyController.class,
    excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
class GameLobbyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameLobbyService lobbyService;
    @MockBean
    private UserService userService;
    @MockBean
    private GameService gameService;

    User testUser;

    @Mock
    GameLobby mockLobby;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("Pepito el de los Palotes");

        when(userService.getLoggedInUser()).thenReturn(Optional.ofNullable(testUser));

        when(lobbyService.getLobbyById(any(Integer.class))).thenReturn(Optional.ofNullable(mockLobby));

    }

    @Test
    @WithMockUser
    void shouldShowLobbyScreen() throws Exception {
        mockMvc.perform(get("/lobby/666"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("lobby", mockLobby));
    }

    @Test
    @WithMockUser
    void shouldRedirectToGameWhenStarted() throws Exception {
        Game game = new Game();
        game.setId(777);

        when(mockLobby.gameStarted()).thenReturn(true);
        when(mockLobby.getGame()).thenReturn(game);

        mockMvc.perform(get("/lobby/666"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/games/777"));
    }

    @Test
    @WithMockUser
    void shouldLeaveGameAsSpectator() throws Exception {
        mockMvc.perform(post("/lobby/7").with(csrf()))
            .andExpect(status().is3xxRedirection());
        verify(mockLobby,never()).removeUser(any(User.class));
        verify(lobbyService,never()).deleteLobby(any(GameLobby.class));
    }

    @Test
    @WithMockUser
    void shouldLeaveGameAsPlayer() throws Exception {
        when(mockLobby.getJoinedUsers()).thenReturn(List.of(testUser));

        mockMvc.perform(post("/lobby/7").with(csrf()))
            .andExpect(status().is3xxRedirection());
        verify(mockLobby).removeUser(any(User.class));
        verify(lobbyService,never()).deleteLobby(any(GameLobby.class));
    }

    @Test
    @WithMockUser
    void shouldDeleteGame() throws Exception {
        when(mockLobby.getLeaderUser()).thenReturn(testUser);

        mockMvc.perform(post("/lobby/7").with(csrf()))
            .andExpect(status().is3xxRedirection());
        verify(lobbyService).deleteLobby(any(GameLobby.class));
    }

    @Test
    @WithMockUser
    void shouldShowJoinLobbyForm() throws Exception {
        mockMvc.perform(get("/lobby/"))
            .andExpect(status().isOk())
            .andExpect(view().name("gameLobbies/joinGameLobbyForm"));
    }

    @Test
    @WithMockUser
    void shouldJoinLobbyWithForm() throws Exception {
        when(mockLobby.isAcceptingNewPlayers()).thenReturn(true);

        mockMvc.perform(post("/lobby/")
            .param("roomCode","666").with(csrf())
            .param("spectate","false"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/lobby/" + 666));
        verify(mockLobby).addUser(any(User.class));
    }

    @Test
    @WithMockUser
    void shouldNotJoinLobbyWhenFull() throws Exception {
        when(mockLobby.isAcceptingNewPlayers()).thenReturn(false);

        mockMvc.perform(post("/lobby/")
                .with(csrf())
                .param("roomCode","666")
                .param("spectate","false"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message", "Game is full!"));

    }

    @Test
    @WithMockUser
    void shouldJoinLobbyWhenSpectating() throws Exception {
        mockMvc.perform(post("/lobby/")
                .param("roomCode","666").with(csrf())
                .param("spectate","true"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/lobby/" + 666));
        verify(mockLobby,never()).addUser(any(User.class));
    }

    @Test
    @WithMockUser
    void shouldJoinLobbyWhenAlreadyAPlayer() throws Exception {
        when(mockLobby.getJoinedUsers()).thenReturn(List.of(testUser));

        mockMvc.perform(post("/lobby/")
                .with(csrf())
                .param("roomCode","666"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/lobby/" + 666));
        verify(mockLobby,never()).addUser(any(User.class));
    }

    @Test
    @WithMockUser
    void shouldNotJoinLobbyWithNoCode() throws Exception {
        mockMvc.perform(post("/lobby/")
                .param("spectate","false").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("gameLobbies/joinGameLobbyForm"))
            .andExpect(model().attribute("message","You must specify a game to join"));
    }

    @Test
    @WithMockUser
    void shouldNotJoinLobbyWithInvalidCode() throws Exception {
        when(lobbyService.getLobbyById(any(Integer.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/lobby/")
                .param("roomCode","555").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("gameLobbies/joinGameLobbyForm"))
            .andExpect(model().attribute("message","No room exists with id 555"));
    }

    @Test
    @WithMockUser
    void shouldNotJoinLobbyWhenPlaying() throws Exception {
        when(lobbyService.userIsPlaying(any(User.class))).thenReturn(true);

        mockMvc.perform(post("/lobby/")
                .param("roomCode","555").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("gameLobbies/joinGameLobbyForm"))
            .andExpect(model().attribute("message","You are already in a game!"));
    }

    @Test
    @WithMockUser
    void shouldShowCreateLobbyForm() throws Exception {

        mockMvc.perform(get("/lobby/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("gameLobbies/createGameLobbyForm"))
            .andExpect(model().attributeExists("gameLobby"));
    }

    @Test
    @WithMockUser
    void shouldCreateLobby() throws Exception {
        mockMvc.perform(post("/lobby/new")
                .with(csrf())
                .param("maxPlayers","2"))
            .andExpect(status().is3xxRedirection());
        verify(lobbyService).saveLobby(any(GameLobby.class));
    }

    @Test
    @WithMockUser
    void shouldNotCreateLobbyWithNoMaxPlayers() throws Exception {
        mockMvc.perform(post("/lobby/new")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("gameLobbies/createGameLobbyForm"))
            .andExpect(model().attributeHasFieldErrors("gameLobby","maxPlayers"));
        verify(lobbyService,never()).saveLobby(any(GameLobby.class));
    }

    @Test
    @WithMockUser
    void shouldNotCreateLobbyWhenPlaying() throws Exception {
        when(lobbyService.userIsPlaying(any(User.class))).thenReturn(true);
        mockMvc.perform(post("/lobby/new")
                .with(csrf())
                .param("maxPlayers","2"))
            .andExpect(status().isOk())
            .andExpect(view().name("gameLobbies/createGameLobbyForm"))
            .andExpect(model().attribute("message", "You are already in a game!"));
        verify(lobbyService,never()).saveLobby(any(GameLobby.class));
    }



    @Test
    @WithMockUser
    void shouldStartGame() throws Exception {
        Game game = new Game();
        game.setId(5);
        when(mockLobby.getLeaderUser()).thenReturn(testUser);
        when(gameService.createNewGameFromLobby(any(GameLobby.class))).thenReturn(game);

        mockMvc.perform(get("/lobby/888/newGame"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/games/5"));
        verify(gameService).saveGame(any(Game.class));
    }

    @Test
    @WithMockUser
    void shouldNotStartGameWhenNotLeader() throws Exception {

        mockMvc.perform(get("/lobby/888/newGame"))
            .andExpect(status().is3xxRedirection());
        verify(gameService,never()).saveGame(any(Game.class));
    }
}
