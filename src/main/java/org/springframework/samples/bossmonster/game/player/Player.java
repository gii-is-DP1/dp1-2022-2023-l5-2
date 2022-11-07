package org.springframework.samples.bossmonster.game.player;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Player extends BaseEntity {

    @OneToOne
    private User user;
    private Integer health;
    private Integer souls;

    // @OneToOne
    // private List<Card> hand;
    // private Dungeon dungeon;  //TODO

}
