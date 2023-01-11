package org.springframework.samples.bossmonster.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.bossmonster.game.chat.Chat;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {
    List<Game> findAll();
    @Query(value = "SELECT c from Chat c WHERE c.id=?1")
    Chat findChat(@Param(value = "id") Integer gameChat);

    @Query("SELECT l FROM Game l WHERE l.active=TRUE")
    List<Game> findActiveGames();
}
