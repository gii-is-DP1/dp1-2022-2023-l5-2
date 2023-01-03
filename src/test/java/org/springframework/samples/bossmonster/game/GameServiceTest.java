package org.springframework.samples.bossmonster.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.bossmonster.game.player.PlayerService;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.user.UserService;

@SpringBootTest()
@ExtendWith(MockitoExtension.class)
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
    private GameBuilder gameBuilder;


    @BeforeEach
    void setUp() {
        gameService = new GameService(gameRepository, playerService, gameBuilder);
    }

    @Test
    void saveGame() {
    }

    @Test
    void createNewGameFromLobby() {
        Integer previousGames = gameService.findAllGames().size();
        Integer previousPlayers = playerService.findAllPlayers().size();

        var users = userService.findAllUsers().subList(0,2);
        var lobby = new GameLobby();
        lobby.setJoinedUsers(users);
        Game game = gameService.createNewGameFromLobby(lobby);

        Integer currentGames = gameService.findAllGames().size();
        Integer currentPlayers = playerService.findAllPlayers().size();

        assertEquals(previousGames + 1, currentGames);
        assertEquals(previousPlayers + users.size(), currentPlayers);
        assertFalse(game.getPlayers().size() == 0);
    }

    @Test
    void findGame() {
    }
}
