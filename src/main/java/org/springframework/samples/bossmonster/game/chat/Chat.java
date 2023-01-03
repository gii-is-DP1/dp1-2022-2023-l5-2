package org.springframework.samples.bossmonster.game.chat;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.bossmonster.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chats")
public class Chat extends BaseEntity{

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chat")
    private List<Message> messages;

}
