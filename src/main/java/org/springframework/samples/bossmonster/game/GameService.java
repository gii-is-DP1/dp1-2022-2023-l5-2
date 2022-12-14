package org.springframework.samples.bossmonster.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.player.PlayerService;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    GameRepository repo;
    PlayerService playerService;
    GameBuilder gameBuilder;

    @Autowired
    public GameService(GameRepository repo, PlayerService playerService, GameBuilder gameBuilder) {
        this.repo=repo;
        this.playerService=playerService;
        this.gameBuilder=gameBuilder;
    }

    @Transactional
    public Game saveGame(Game g) {
        return repo.save(g);
    }

    @Transactional
    public Game createNewGameFromLobby(GameLobby lobby) {
        Game newGame = gameBuilder.buildNewGame(lobby);
        repo.save(newGame);
        return newGame;
    }

    @Transactional(readOnly = true)
    public Optional<Game> findGame(Integer id) {
        return repo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Game> findAllGames() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Game> findActiveGames() {
        return repo.findActiveGames();
    }

}
