package org.springframework.samples.bossmonster.game.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatService {

    private ChatRepository repo;
    private MessageRepository repo2;

    private static List<String> palabrasCensuradas=List.of(
        "no me gusta dp", 
        "no me toca nada bueno",
        "leche antes de los cereales",
        "palabrota",
        "twitter");

    @Autowired
    public ChatService(ChatRepository chatRepository, MessageRepository repo2){
        this.repo = chatRepository;
        this.repo2=repo2;

    }
    public Optional<Chat> findById(Integer id){
        return repo.findById(id);
    }
    public List<Message> getMessages(Integer id){
        return repo2.getMessages(id);
    }

    public void addMessage(Message message){
        repo2.save(message);
    }

    @Transactional
    public void createChat(Chat chat){
        repo.save(chat);
    }

    public Boolean estaCensurada(String words){
        if (words != null){
            for(Integer i=0; i<palabrasCensuradas.size();i++){
                String palabraCensurada= palabrasCensuradas.get(i).toLowerCase();
                words = words.toLowerCase();
                if(words.contains(palabraCensurada)){
                    return true;
                }
            }
        }

        return false;
    }

    public String cambiarPalabrasCensuradas(String words){
        String result=words.toLowerCase();
        for(Integer i=0; i<palabrasCensuradas.size();i++){
            String palabraCensurada= palabrasCensuradas.get(i).toLowerCase();
            if(result.contains(palabraCensurada)){
                result=result.replaceAll(palabraCensurada, "*******");
            }
        }
        return result;
    }
}

