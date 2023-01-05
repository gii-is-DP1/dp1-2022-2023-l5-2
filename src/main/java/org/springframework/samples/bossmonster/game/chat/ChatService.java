package org.springframework.samples.bossmonster.game.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatService {
    @Autowired
    private ChatRepository repo;
    @Autowired
    private MessageRepository repo2;

    @Autowired
    public ChatService(ChatRepository chatRepository){
        this.repo = chatRepository;
    }
    public Optional<Chat> findById(Integer id){
        return repo.findById(id);
    }
    public List<Message> getMessages(Integer id){
        return repo2.getMessages(id);
    }
    public Message findMessageById(Integer id){
        return repo.findMessageId(id);
    }
    public void addMessage(Message message){
        repo2.save(message);
    }
    @Transactional
    public void createChat(Chat chat){
        repo.save(chat);
    }
}

