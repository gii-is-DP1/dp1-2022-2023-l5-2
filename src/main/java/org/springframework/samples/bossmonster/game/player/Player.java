package org.springframework.samples.bossmonster.game.player;

import lombok.Getter;
import lombok.Setter;

import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
@Entity
public class Player extends BaseEntity {

    private PlayerBuilder playerBuilder;

    @OneToOne
    private User user;

    private Integer health;

    private Integer souls;

    // @OneToOne
    // private Dungeon dungeon;

    @OneToMany
    private List<Card> hand;

    //@OneToOne
    //Dungeon dungeon;

    public void buildNewPlayer(User user) {
        playerBuilder.buildPlayerStartingHand();
        playerBuilder.buildPlayerDungeon();
        playerBuilder.buildPlayerStats();
        playerBuilder.buildPlayerUser(user);
    }

    public Player getNewPlayer() { return playerBuilder.getNewPlayer(); }


}
