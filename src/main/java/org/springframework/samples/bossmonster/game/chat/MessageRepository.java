package org.springframework.samples.bossmonster.game.chat;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends CrudRepository<Message,Integer>{
    @Query(value = "SELECT * FROM messages m WHERE m.chat=?1",nativeQuery = true)
    List<Message> getMessages(@Param(value = "chat_id") Integer id);
}
