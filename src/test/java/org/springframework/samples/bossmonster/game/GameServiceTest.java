package org.springframework.samples.bossmonster.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.samples.bossmonster.game.player.PlayerService;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.gameLobby.GameLobbyService;
import org.springframework.samples.bossmonster.social.FriendRequestService;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes =FriendRequestService.class))
@ExtendWith(MockitoExtension.class)
@Import(GameBuilder.class)
class GameServiceTest {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerService playerService;
    @Autowired
    UserService userService;
    @Autowired
    private GameService gameService;
    @Autowired
    GameLobbyService lobbyService;
    @Autowired
    private GameBuilder gameBuilder;


    @BeforeEach
    void setUp() {
        gameService = new GameService(gameRepository, playerService, gameBuilder);
    }

    @Test
    void saveGame() {
        Game game = new Game();
        game.setPreviousChoices(new Stack<>());
        Integer previousGames = gameService.findAllGames().size();
        gameService.saveGame(game);
        assertTrue(gameService.findAllGames().size() > previousGames);
    }

    @Test
    void createNewGameFromLobby() {
        Integer previousGames = gameService.findAllGames().size();
        Integer previousPlayers = playerService.findAllPlayers().size();

        var users = userService.findAllUsers().subList(0,2);
        var lobby = new GameLobby();
        lobby.setJoinedUsers(users);
        lobby.setMaxPlayers(2);
        lobby.setLeaderUser(users.get(0));
        lobbyService.saveLobby(lobby);

        Game game = gameService.createNewGameFromLobby(lobby);

        Integer currentGames = gameService.findAllGames().size();
        Integer currentPlayers = playerService.findAllPlayers().size();

        assertEquals(previousGames + 1, currentGames);
        assertEquals(previousPlayers + users.size(), currentPlayers);
        assertFalse(game.getPlayers().size() == 0);
    }

    @Test
    void findGame() {
        Game game = new Game();
        game.setPreviousChoices(new Stack<>());
        gameService.saveGame(game);

        List<Game> foundGame = gameService.findAllGames();
        assertFalse(foundGame.isEmpty());
    }
}
