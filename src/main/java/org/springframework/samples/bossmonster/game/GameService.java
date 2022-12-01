package org.springframework.samples.bossmonster.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.game.player.PlayerService;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    GameRepository repo;
    PlayerService playerService;


    @Autowired
    public GameService(GameRepository repo, PlayerService playerService) {
        this.repo=repo;
        this.playerService=playerService;

    }

    public Game saveGame(Game g) {
        return repo.save(g);
    }

    public Game createNewGameFromLobby(GameLobby lobby) {
        GameBuilder gameBuilder = new GameBuilder();
        Game newGame = gameBuilder.buildNewGame(lobby.getJoinedUsers());
        return newGame;
    }

    public Optional<Game> findGame(Integer id) {
        return repo.findById(id);
    }

}
