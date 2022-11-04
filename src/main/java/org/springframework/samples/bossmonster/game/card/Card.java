package org.springframework.samples.bossmonster.game.card;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.model.NamedEntity;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class Card extends NamedEntity {

    private String cardImage;

}
