package org.springframework.samples.bossmonster.game.card.room;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.EffectEnum;
import org.springframework.samples.bossmonster.game.card.TreasureType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@Table(name = "rooms")
@Slf4j
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
        log.debug(String.format("Checking position %s in %s: %s", targetPosition, getTreasure(), Character.getNumericValue(treasure.charAt(targetPosition))));
        return Character.getNumericValue(treasure.charAt(targetPosition));

    }
    public Boolean isAdvanced() {
        return roomType == RoomType.ADVANCED_MONSTER || roomType == RoomType.ADVANCED_TRAP;
    }

    public Boolean isTrapType() {
        return roomType == RoomType.TRAP || roomType == RoomType.ADVANCED_TRAP;
    }

    public Boolean isMonsterType() {
        return roomType == RoomType.MONSTER || roomType == RoomType.ADVANCED_MONSTER;
    }

}
