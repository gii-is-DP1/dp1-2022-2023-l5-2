package org.springframework.samples.bossmonster.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.configuration.SecurityConfiguration;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.gameState.GameState;
import org.springframework.samples.bossmonster.game.player.Player;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = GameController.class,
    excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
public class GameControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    GameService gameService;
    @MockBean
    UserService userService;

    User mainUser = new User();
    User anotherUser = new User();
    Player mainPlayer = new Player();
    Player anotherPlayer = new Player();
    @Spy
    Game game;
    @Mock
    GameState state;

    @BeforeEach
    void setUp() {
        mainUser.setNickname("Mario");
        mainUser.setUsername("mario");
        anotherUser.setNickname("Luigi");
        anotherUser.setUsername("luigi");
        mainPlayer.setUser(mainUser);
        anotherPlayer.setUser(anotherUser);

        game.setPlayers(List.of(mainPlayer,anotherPlayer));
        game.setState(state);
        doReturn(List.of(new Card(), new Card())).when(game).getChoice();
        doReturn(true).when(game).getPlayerHasToChoose(any(Player.class));
        doReturn(mainPlayer).when(game).getCurrentPlayer();
        doNothing().when(game).makeChoice(any(Integer.class));


        when(userService.getLoggedInUser()).thenReturn(Optional.of(mainUser));
        when(gameService.findGame(anyInt())).thenReturn(Optional.of(game));
    }

    @Test
    @WithMockUser
    public void shouldShowGame() throws Exception {
        mockMvc.perform(get("/games/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("games/gameScreen"))
            .andExpect(model().attribute("game",game))
            .andExpect(model().attribute("triggerModal",true));
    }

    @Test
    @WithMockUser
    void shouldNotShowChoiceModal() throws Exception {
        doReturn(false).when(game).getPlayerHasToChoose(any(Player.class));

        mockMvc.perform(get("/games/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("games/gameScreen"))
            .andExpect(model().attribute("game",game))
            .andExpect(model().attributeDoesNotExist("triggerModal"));

    }

    @Test
    @WithMockUser
    void shouldSendChoice() throws Exception {
        mockMvc.perform(post("/games/1")
                .with(csrf())
                .param("choice","0"))
            .andExpect(status().isOk())
            .andExpect(view().name("games/gameScreen"));
        verify(game).makeChoice(0);
    }

    @Test
    @WithMockUser
    void shouldNotSendChoice() throws Exception {
        doReturn(false).when(game).getPlayerHasToChoose(any());
        mockMvc.perform(post("/games/1")
                .with(csrf())
                .param("choice","0"))
            .andExpect(status().isOk())
            .andExpect(view().name("games/gameScreen"));
        verify(game,never()).makeChoice(any());
    }

    @Test
    @WithMockUser
    void shouldGetActiveGames() throws Exception {
        List<Game> games = List.of(game);
        when(gameService.findActiveGames()).thenReturn(games);

        mockMvc.perform(get("/games/listActiveGames"))
            .andExpect(status().isOk())
            .andExpect(view().name("games/currentGames"))
            .andExpect(model().attribute("game",games));
    }


}
