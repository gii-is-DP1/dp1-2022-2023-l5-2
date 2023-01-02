package org.springframework.samples.bossmonster.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {
    List<Game> findAll();

    @Query("SELECT l FROM Game l WHERE l.active=TRUE")
    List<Game> findActiveGames();
}
