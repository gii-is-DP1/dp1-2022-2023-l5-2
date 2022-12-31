package org.springframework.samples.bossmonster.game.player;

import lombok.Getter;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import javax.persistence.*;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

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

    public void damageRandomHeroInDungeonPosition(Integer position, Integer damage) {
        List<HeroCardStateInDungeon> heroesInSlot = getDungeon().getRoomSlots()[position].getHeroesInRoom();
        if (!heroesInSlot.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(heroesInSlot.size());
            HeroCardStateInDungeon chosenHero = heroesInSlot.get(index);
            chosenHero.dealDamage(damage);
            if (chosenHero.isDead()) {
                getDungeon().getRoomSlots()[position].removeHero(chosenHero);
                addSoulsFromKilledHero(chosenHero);
            }
        }
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

    public void heroAdvanceRoomDungeon() {
        for(int i = 4; i >= 0; i --) {
            DungeonRoomSlot roomSlot = dungeon.getRoomSlots()[i];
            Integer dealtDamage = roomSlot.getRoomTrueDamage();
            Iterator<HeroCardStateInDungeon> heroes = roomSlot.getHeroesInRoom().iterator();
            while(heroes.hasNext()) {
                HeroCardStateInDungeon hero = heroes.next();
                hero.dealDamage(dealtDamage);
                if (hero.isDead()) addSoulsFromKilledHero(hero);
                else {
                    if (!isDungeonLastRoom(i)) {
                        log.debug("Moving hero...");
                        dungeon.getRoomSlots()[i - 1].addHero(hero);
                    }
                    else {
                        log.debug("Reached last room, damaging player");
                        removeHealthFromUndefeatedHero(hero);
                    }
                }
                heroes.remove();

            }
        }
    }

    public Boolean isDungeonLastRoom(Integer position) {
        return position == 0;
    }

    public Boolean isDead() {
        return health <= 0;
    }

    public void heroAutomaticallyMovesAfterDestroyingRoom(Integer position) {
        List<HeroCardStateInDungeon> affectedHeroes = dungeon.getRoomSlots()[position].getHeroesInRoom();
        for (HeroCardStateInDungeon hcsd: affectedHeroes) {
            dungeon.getRoomSlots()[position].removeHero(hcsd);
            if (isDungeonLastRoom(position)) removeHealthFromUndefeatedHero(hcsd);
            else dungeon.getRoomSlots()[position-1].addHero(hcsd);
        }
    }

}
