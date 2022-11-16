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

    public Game newGameFromLobby(GameLobby l) {
        Game g = new Game();

        List<Player> players = l.getJoinedUsers()
            .stream().map(u->playerService.playerFromUser(u))
            .collect(Collectors.toList());
        g.setPlayers(players);

        return g;
    }

    public Optional<Game> findGame(Integer id) {
        return repo.findById(id);
    }

}
