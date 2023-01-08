package org.springframework.samples.bossmonster.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.game.chat.Chat;
import org.springframework.samples.bossmonster.game.chat.ChatService;
import org.springframework.samples.bossmonster.game.chat.Message;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = GameService.class))
public class ChatServiceTest {

    @Autowired
    protected ChatService chatService;

    @Test
    @DisplayName("Find the chat Entity with the correct id")
    public void shouldFindTheCorrectChat(){
        
        Optional<Chat> chat = chatService.findById(0);
        assertTrue(chat.isPresent());
        assertEquals(0, chat.get().getId());
        chat = chatService.findById(-1);
        assertFalse(chat.isPresent());
    }

    @Test
    @DisplayName("Find the messages with the correct chat id")
    public void shouldFindMessages(){
        
        List<Message> messages = chatService.getMessages(0);
        assertNotNull(messages);
        assertTrue(messages.size() >= 3);

        messages = chatService.getMessages(-1);
        assertNotNull(messages);
        assertEquals(0, messages.size());
    }

    @Test
    @DisplayName("Create chat and Save the message")
    // This test both, the creation of the chat, and the saving message functions.
    public void shouldCreateChatAndSaveMessage(){
        
        Chat chat = new Chat();
        chatService.createChat(chat);
        Message message = new Message();
        message.setWords("Word");
        message.setChat(chat);
        chatService.addMessage(message);
        List<Message> messages = chatService.getMessages(chat.getId());
        assertNotNull(messages);
        assertEquals(1, messages.size());
        assertEquals("Word", messages.get(0).getWords());
    }

    //estaCensurada Tests
    @Test
    public void shouldFindCensoredWorld() {
    String words = "Por supuesto que no me gusta DP";
    Boolean result = chatService.estaCensurada(words);
    assertTrue(result);
    }

    @Test
    public void shouldNotFindCensoredWorld() {
    String words = "DP1 es la mejor asignatura";
    Boolean result = chatService.estaCensurada(words);
    assertFalse(result);
    }

    @Test
    public void shouldNotCensorAnEmptyString() {
    String words = "";
    Boolean result = chatService.estaCensurada(words);
    assertFalse(result);
    }

    @Test
    public void shouldNotCensorANull() {
    String words = null;
    Boolean result = chatService.estaCensurada(words);
    assertFalse(result);
    }

    @Test
    public void shouldCensorTheWord(){
    String prueba1 = "Eres un Palabrota";
    assertEquals("eres un *******", chatService.cambiarPalabrasCensuradas(prueba1));
    }






    
}
