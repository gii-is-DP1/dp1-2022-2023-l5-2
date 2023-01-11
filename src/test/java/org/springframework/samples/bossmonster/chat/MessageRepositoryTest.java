package org.springframework.samples.bossmonster.chat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.bossmonster.game.chat.Message;
import org.springframework.samples.bossmonster.game.chat.MessageRepository;
import org.springframework.stereotype.Repository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class MessageRepositoryTest {
    
    @Autowired
    private MessageRepository messageRepository;

    @Test
    @DisplayName("Get the messages from a chat")
    public void shouldGetMessages() {
        List<Message> messages = messageRepository.getMessages(0);
        assertTrue(messages.size() > 0);
    }
    
    @Test
    @DisplayName("Delete all messages from an User")
    public void shouldDeleteAllMessagesFromUser() {
        messageRepository.deleteAllMesagesFromUser("jessolort");
        List<Message> messages = messageRepository.getMessages(0);
        assertTrue(messages.stream().noneMatch(m -> m.getSender().getUsername().equals("johndoe")));
    }


}
