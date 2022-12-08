package org.springframework.samples.bossmonster.game.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PlayerService {

    PlayerRepository repo;

    @Autowired
    public PlayerService(PlayerRepository repo) {this.repo=repo;}

    public Player save(Player p) {return repo.save(p);}

    public Player playerFromUser(User u) {
        Player p = new Player();
        p.setUser(u);
        p.setSouls(0);
        p.setHand(new ArrayList<>());
        p.setHealth(5);
        return repo.save(p);
    }

    public List<Player> findAllPlayers() {
        return repo.findAll();
    }
}
