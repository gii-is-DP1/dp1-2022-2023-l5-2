package org.springframework.samples.bossmonster.game.chat;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.bossmonster.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Chat extends BaseEntity{

    // @NotEmpty
    // private List<Message> chad;
}
