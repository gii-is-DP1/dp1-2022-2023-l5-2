package org.springframework.samples.bossmonster.game.card.room;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.player.Player;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "rooms")
public class RoomCard extends Card {

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    private String treasure;
    private Integer damage;

    @ManyToOne
    private Player hand;

    @ManyToOne
    @JoinColumn(name = "passive_trigger_id")
    private RoomPassiveTrigger passiveTrigger;
    // private Integer roomPassiveActionId;     CAMBIAR A ENUM???
}
