package org.springframework.samples.bossmonster.game.chat;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends CrudRepository<Message,Integer>{

    @Query(value = "SELECT m FROM Message m WHERE m.chat.id=?1")
    List<Message> getMessages(@Param(value = "chat_id") Integer id);

    @Query(value = "DELETE FROM Message m WHERE m.sender.username=?1")
    @Modifying
    void deleteAllMesagesFromUser(@Param(value = "username") String id);
}
