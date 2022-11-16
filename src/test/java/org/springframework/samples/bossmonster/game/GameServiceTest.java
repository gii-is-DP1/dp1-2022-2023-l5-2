package org.springframework.samples.bossmonster.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.bossmonster.game.player.PlayerService;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;
    @MockBean
    private PlayerService playerService;
    protected GameService gameService;



    @BeforeEach
    void setUp() {
        gameService = new GameService(gameRepository, playerService);
    }

    @Test
    void saveGame() {
    }

    @Test
    void newGameFromLobby() {
        GameLobby lobby = new GameLobby();



    }

    @Test
    void findGame() {
    }
}
