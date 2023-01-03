package org.springframework.samples.bossmonster.game.chat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.samples.bossmonster.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    @Size(min = 0, max = 150)
    private String words;

    @ManyToOne
    @JoinColumn(name = "chat")
    private Chat chat;

    
    
}
