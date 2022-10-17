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
@Table(name = "roomcards")
public class RoomCard extends Card {

    @Column(name = "roomType")
    @NotEmpty
    private RoomType roomType;

    @Column(name = "treasure")
    @NotEmpty
    private String treasure;

    @Column(name = "damage")
    @NotEmpty
    private Integer damage;

    @Column(name = "roomPassiveTrigger")
    @NotEmpty
    private RoomPassiveTrigger roomPassiveTrigger;
    // private Integer roomPassiveActionId;     CAMBIAR A ENUM???

    public RoomCard() {
    }

    public RoomCard(@NotEmpty RoomType roomType, @NotEmpty String treasure, @NotEmpty Integer damage,
            @NotEmpty RoomPassiveTrigger roomPassiveTrigger) {
        this.roomType = roomType;
        this.treasure = treasure;
        this.damage = damage;
        this.roomPassiveTrigger = roomPassiveTrigger;
    }

    
}
