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
    private EffectTarget effectTacget;

    // TO DO: clase por hacer, revisar datos y en la base de datos.

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getTreasure() {
        return treasure;
    }

    public void setTreasure(String treasure) {
        this.treasure = treasure;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public RoomPassiveTrigger getPassiveTrigger() {
        return passiveTrigger;
    }

    public void setPassiveTrigger(RoomPassiveTrigger passiveTrigger) {
        this.passiveTrigger = passiveTrigger;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public EffectTarget getEffectTacget() {
        return effectTacget;
    }

    public void setEffectTacget(EffectTarget effectTacget) {
        this.effectTacget = effectTacget;
    }

}
