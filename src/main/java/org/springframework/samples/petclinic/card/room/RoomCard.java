package org.springframework.samples.petclinic.card.room;

import javax.persistence.Entity;
import org.springframework.samples.petclinic.card.Card;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RoomCard extends Card {
    private RoomType roomType;
    private String treasure;
    private Integer damage;
    private RoomPassiveTrigger roomPassiveTrigger;
    // private Integer roomPassiveActionId;     CAMBIAR A ENUM???
}
