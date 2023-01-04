package org.springframework.samples.bossmonster.game.chat;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends CrudRepository<Chat,Integer> {
    
    @Query(value = "INSERT INTO messages(words,chat) VALUES (?1,?2)",nativeQuery = true)
    void addMessage(@Param(value = "message") Message message, @Param(value = "chatId") Integer chatId);
    @Query(value = "SELECT m.words FROM messages m WHERE m.chat=?1",nativeQuery = true)
    List<String> getMessages(@Param(value = "chat_id") Integer id);
}
