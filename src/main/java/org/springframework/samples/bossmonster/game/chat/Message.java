package org.springframework.samples.bossmonster.game.chat;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message {

    @NotEmpty
    @Id
    private Long id_message;

    @NotEmpty
    private String body;

    @NotEmpty
    private Date date; 
}