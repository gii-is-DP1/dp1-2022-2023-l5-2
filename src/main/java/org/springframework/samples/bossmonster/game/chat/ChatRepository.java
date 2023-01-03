package org.springframework.samples.bossmonster.game.chat;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends CrudRepository<Chat,Integer> {
    
    @Query(value = "INSERT INTO messages(words,chat) VALUES (?1,?2)",nativeQuery = true)
    void addMessage(@Param(value = "message") String message, @Param(value = "chatId") Integer chatId);
    
}
