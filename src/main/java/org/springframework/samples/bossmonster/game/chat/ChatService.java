package org.springframework.samples.bossmonster.game.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    private ChatRepository repo;

    @Autowired
    public ChatService(ChatRepository chatRepository){
        this.repo = chatRepository;
    }
    Optional<Chat> findById(Integer id){
        return repo.findById(id);
    }
    Message findMessagesById(Integer id){
        return repo.findMessageById(id);
    }
    List<String> getMessages(Integer id){
        return repo.getMessages(id);
    }
    public void addMessage(Message message, Integer chatId){
        repo.addMessage(message, chatId);
    }
}

