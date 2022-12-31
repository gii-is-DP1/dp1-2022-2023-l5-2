package org.springframework.samples.bossmonster.game.card.room;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.EffectEnum;
import org.springframework.samples.bossmonster.game.card.TreasureType;

import lombok.Getter;
import lombok.Setter;


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
    private EffectEnum effect;
    

    public Integer parseTreasureAmount(TreasureType targetTreasure) {

        Integer targetPosition;
        switch (targetTreasure) {
            case BOOK:  { targetPosition = 0; break; }
            case SWORD: { targetPosition = 1; break; }
            case CROSS: { targetPosition = 2; break; }
            case BAG:   { targetPosition = 3; break; }
            default: return null;
        }
        return Character.getNumericValue(treasure.charAt(targetPosition));

    }

}
