package org.springframework.samples.bossmonster.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.bossmonster.game.chat.Chat;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {
    List<Game> findAll();
    @Query(value = "SELECT c from chats c ON c.id=?1",nativeQuery = true)
    Chat findChat(@Param(value = "id") Integer gameChat);
}
