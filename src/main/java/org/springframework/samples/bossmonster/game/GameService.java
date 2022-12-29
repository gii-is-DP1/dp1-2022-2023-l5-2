package org.springframework.samples.bossmonster.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.gameState.GamePhase;
import org.springframework.samples.bossmonster.game.player.PlayerService;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    GameRepository repo;
    PlayerService playerService;
    GameBuilder gameBuilder;

    private static final Integer PLAYER_HAND_CARD_LIMIT = 5;

    @Autowired
    public GameService(GameRepository repo, PlayerService playerService, GameBuilder gameBuilder) {
        this.repo=repo;
        this.playerService=playerService;
        this.gameBuilder=gameBuilder;
    }

    public Game saveGame(Game g) {
        return repo.save(g);
    }

    public Game createNewGameFromLobby(GameLobby lobby) {
        Game newGame = gameBuilder.buildNewGame(lobby);
        repo.save(newGame);
        return newGame;
    }

    public Optional<Game> findGame(Integer id) {
        return repo.findById(id);
    }

    public List<Game> findAllGames() {
        return repo.findAll();
    }

}
