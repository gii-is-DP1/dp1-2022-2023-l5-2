package org.springframework.samples.bossmonster.game.player;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
}
