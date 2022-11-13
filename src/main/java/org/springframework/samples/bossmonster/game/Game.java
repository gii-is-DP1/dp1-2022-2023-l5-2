package org.springframework.samples.bossmonster.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends BaseEntity{

    @OneToMany
    List<Player> players;
    boolean active;

    @OneToMany
    List<Card> discardPile;
    @OneToMany
    List<HeroCard> heroPile;
    @OneToMany
    List<SpellCard> spellPile;
    @OneToMany
    List<RoomCard> roomPile;

    @OneToMany
    List<HeroCard> city;


    public void moveCard() {}

    @OneToOne
    private GameResult result;

}
