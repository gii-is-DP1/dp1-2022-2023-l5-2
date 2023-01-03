package org.springframework.samples.bossmonster.game.chat;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ChatController {
    
    private static final String VIEWS_CHAT = "/games/chatScreen";
    private static final String ADD_MESSAGE_VIEW ="___";


    private final ChatService service;

    @Autowired
    public ChatController(ChatService chatService){
        this.service = chatService;
    }
    @GetMapping("/chat/{chatId}")
    public ModelAndView getChat(@PathVariable Integer chatId){
        ModelAndView result=new ModelAndView(VIEWS_CHAT);
        List<String> messages= service.getMessages(chatId);

        result.addObject("messages", messages);
        return result;
    }
    @GetMapping("/chat/{chatId}/new")
    public String showAddMessageForm(@PathVariable Integer chatId, Map<String, Object> model){
        Message message= new Message();
        Chat chat= service.findById(chatId).get();
        message.setChat(chat);
        model.put("message",message);
        return ADD_MESSAGE_VIEW;
    }
    @Transactional
    @PostMapping("/chat/{chatId}/new")
    public ModelAndView proccessNewMessage(@PathVariable Integer chatId, @Valid Message message, BindingResult br){
        ModelAndView result=new ModelAndView();
        if(br.hasErrors()){
            result = new ModelAndView(ADD_MESSAGE_VIEW);
			result.addObject("message", "Can't send message. Invalid values are present");
        }else if(2==chatId){
            result = new ModelAndView(ADD_MESSAGE_VIEW);
            result.addObject("message", "¡Esa lengua chiquillo! ¿Besas a tu madre con esa boca?");
        }else{
                service.addMessage(message, chatId);
                result.setViewName("redirect:/chat/"+chatId);
        }
        return result;
    }

}
