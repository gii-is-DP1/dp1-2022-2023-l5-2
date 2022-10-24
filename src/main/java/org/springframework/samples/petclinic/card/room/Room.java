package org.springframework.samples.petclinic.card.room;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import org.springframework.samples.petclinic.card.Card;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rooms")
public class Room extends Card {

    @Column(name = "roomType")
    @NotEmpty
    private RoomType roomType;

    @Column(name = "treasure")
    @NotEmpty
    private String treasure;

    @Column(name = "damage")
    @NotEmpty
    private Integer damage;


    @Column(name = "passiveTrigger")
    @NotEmpty
    private RoomPassiveTrigger passiveTrigger;

    // private Integer roomPassiveActionId;     CAMBIAR A ENUM???

    public Room() {}

    public Room(@NotEmpty RoomType roomType, @NotEmpty String treasure, @NotEmpty Integer damage,
            @NotEmpty RoomPassiveTrigger passiveTrigger) {
        this.roomType = roomType;
        this.treasure = treasure;
        this.damage = damage;
        this.passiveTrigger = passiveTrigger;
    }  
}
