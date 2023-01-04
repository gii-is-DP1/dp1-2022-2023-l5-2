package org.springframework.samples.bossmonster.game.chat;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends CrudRepository<Chat,Integer> {
    @Query(value = "SELECT m FROM messages WHERE m.id=?1",nativeQuery = true)
    Message findMessageId(@Param(value = "id") Integer id);
}
