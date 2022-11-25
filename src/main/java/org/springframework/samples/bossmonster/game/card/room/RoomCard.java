package org.springframework.samples.bossmonster.game.card.room;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.game.card.Card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@Table(name = "rooms")
public class RoomCard extends Card {

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;
    
    private String treasure;
    private Integer damage;

    @Enumerated(EnumType.STRING)
    @Column(name = "passive_trigger")
    private RoomPassiveTrigger passiveTrigger;

    @Enumerated(EnumType.STRING)
    @Column(name="effect")
    private Effect effect;
    
    @Enumerated(EnumType.STRING)
    @Column(name="effect_target")
    private EffectTarget effectTarget;

}
