package org.springframework.samples.bossmonster.game.chat;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {
    
    private static final String VIEWS_CHAT = "/games/chatScreen";

    @Autowired
    ChatService service;
    @Autowired
    GameService gameService;
    @Autowired
    UserService userService;

    @Autowired
    public ChatController(ChatService chatService, GameService gameService, UserService userService){
        this.service = chatService;
    }
    @GetMapping("games/{gameId}/chat")
    public ModelAndView getChat(@PathVariable Integer gameId, HttpServletResponse response){
        ModelAndView result=new ModelAndView(VIEWS_CHAT);
        Chat chat=gameService.findGame(gameId).get().getChat();
        List<Message> messages= service.getMessages(chat.getId());
        response.addHeader("Refresh", "60");
        result.addObject("messages", messages);
        result.addObject("gameId", gameId);
        return result;
    }
    @Transactional
    @PostMapping("games/{gameId}/chat")
    public ModelAndView sendMessage(@PathVariable Integer gameId, String words){
        ModelAndView result= new ModelAndView();
        Message message=new Message();
        User user=userService.getLoggedInUser().get();
        Chat chat=gameService.findGame(gameId).get().getChat();
        
        message.setWords(words);
        message.setChat(chat);
        message.setSender(user);

        if(service.estaCensurada(words)){
            message.setWords(service.cambiarPalabrasCensuradas(words));
            service.addMessage(message);
            result.setViewName("redirect:/games/"+gameId+"/chat");
        }else if(message.getWords().length()==0){
            result.setViewName("redirect:/games/"+gameId+"/chat");
        }
        else{
            service.addMessage(message);
            result.setViewName("redirect:/games/"+gameId+"/chat");
        }
        return result;
    }
}
