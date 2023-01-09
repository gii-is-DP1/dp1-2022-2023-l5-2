package org.springframework.samples.bossmonster.chat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.configuration.SecurityConfiguration;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.game.chat.Chat;
import org.springframework.samples.bossmonster.game.chat.ChatController;
import org.springframework.samples.bossmonster.game.chat.ChatService;
import org.springframework.samples.bossmonster.game.chat.Message;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ChatController.class,
                excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
                excludeAutoConfiguration = SecurityConfiguration.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Get the chat")
    @WithMockUser(username = "pepe")
    void shouldGetChat() throws Exception {

        Chat chat = new Chat();
        chat.setId(1);
        Game game = new Game();
        game.setChat(chat);
        Optional<Game> optionalGame = Optional.of(game);

        List<Message> messages = new ArrayList<>();
        Message message1 = new Message();
        message1.setId(1);
        message1.setWords("Hello");
        messages.add(message1);
        Message message2 = new Message();
        message2.setId(2);
        message2.setWords("World");
        messages.add(message2);

        when(gameService.findGame(1)).thenReturn(optionalGame);
        when(chatService.getMessages(1)).thenReturn(messages);

        mockMvc.perform(get("/games/1/chat"))
                .andExpect(status().isOk())
                .andExpect(view().name("/games/chatScreen"))
                .andExpect(model().attribute("messages", messages))
                .andExpect(model().attribute("gameId", 1))
                .andExpect(header().string("Refresh", "60"));

        verify(gameService).findGame(1);
        verify(chatService).getMessages(1);
    }
 
    @Test
    @DisplayName("Send the message with censorship")
    @WithMockUser(username = "admin1")
    void shouldSendMessage_censoredWords() throws Exception {
        // Set up mock data
        User user = new User();
        user.setUsername("admin1");
        Chat chat = new Chat();
        Game game = new Game();
        game.setId(1);
        game.setChat(chat);
        when(userService.getLoggedInUser()).thenReturn(Optional.of(user));
        when(gameService.findGame(game.getId())).thenReturn(Optional.of(game));
        when(chatService.estaCensurada(anyString())).thenReturn(true);
        when(chatService.cambiarPalabrasCensuradas(anyString())).thenReturn("***");
        
        

        // Perform the request and check the response
        mockMvc.perform(post("/games/1/chat").with(csrf())
                .param("words", "some censored words"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games/1/chat"));

        // Verify that the service methods were called with the correct arguments
        verify(chatService).estaCensurada("some censored words");
        verify(chatService).cambiarPalabrasCensuradas("some censored words");
        verify(chatService).addMessage(argThat(message -> message.getWords().equals("***")
                && message.getChat().equals(chat)
                && message.getSender().equals(user)));
    }

    @Test
    @WithMockUser(username = "admin1")
    @DisplayName("Send an empty message")
    void shouldSendMessage_emptyMessage() throws Exception {

        User user = new User();
        Chat chat = new Chat();
        Game game = new Game();
        game.setId(1);
        game.setChat(chat);
        when(userService.getLoggedInUser()).thenReturn(Optional.of(user));
        when(gameService.findGame(game.getId())).thenReturn(Optional.of(game));

        mockMvc.perform(post("/games/1/chat").with(csrf())
                .param("words", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games/1/chat"));

        verify(chatService, never()).addMessage(any());
    }

    @Test
    @WithMockUser(username = "admin1")
    @DisplayName("Send a normal message")
    void shouldSendMessage_normalMessage() throws Exception {

        User user = new User();
        Chat chat = new Chat();
        Game game = new Game();
        game.setId(1);
        game.setChat(chat);
        when(userService.getLoggedInUser()).thenReturn(Optional.of(user));
        when(gameService.findGame(game.getId())).thenReturn(Optional.of(game));
        when(chatService.estaCensurada(anyString())).thenReturn(false);

        mockMvc.perform(post("/games/1/chat").with(csrf())
                .param("words", "some normal words"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games/1/chat"));

        verify(chatService).addMessage(argThat(message -> message.getWords().equals("some normal words")
                && message.getChat().equals(chat)
                && message.getSender().equals(user)));
    }
    





}
