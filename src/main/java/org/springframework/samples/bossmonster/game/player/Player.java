package org.springframework.samples.bossmonster.game.player;

import lombok.Getter;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Slf4j
public class Player extends BaseEntity {

    @OneToOne
    private User user;

    private Integer health;

    private Integer souls;

    @OneToOne(cascade = CascadeType.ALL)
    Dungeon dungeon;

    @OneToMany
    private List<Card> hand;

    private Integer eliminatedRound;

    public Card removeHandCard(int cardPosition) {
        return hand.remove(cardPosition);
    }

    public void addHandCard(Card card) {
        hand.add(card);
    }

    public void addSoulsFromKilledHero(HeroCardStateInDungeon hero) {
        Integer value;
        if (hero.getHeroCard().getIsEpic()) value = Game.EPIC_HERO_SOUL_VALUE;
        else value = Game.NORMAL_HERO_SOUL_VALUE;
        souls += value;
    }

    public void removeHealthFromUndefeatedHero(HeroCardStateInDungeon hero) {
        Integer value;
        if (hero.getHeroCard().getIsEpic()) value = Game.EPIC_HERO_SOUL_VALUE;
        else value = Game.NORMAL_HERO_SOUL_VALUE;
        health -= value;
    }

    public Boolean isDead() {
        return health <= 0;
    }

    public String toString() {
        return this.getUser().getNickname();
    }
}
