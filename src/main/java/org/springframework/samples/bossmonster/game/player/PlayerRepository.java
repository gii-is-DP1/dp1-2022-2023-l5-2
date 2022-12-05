package org.springframework.samples.bossmonster.game.player;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    List<Player> findAll();
}
