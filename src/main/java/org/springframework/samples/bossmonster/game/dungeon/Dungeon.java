package org.springframework.samples.bossmonster.game.dungeon;

import java.util.List;

import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.model.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Dungeon extends BaseEntity {

    @OneToOne
    FinalBossCard bossCard;

    @OneToMany
    @OrderColumn
    RoomCard[] rooms;

    Boolean[] roomIsRevealed;

    Integer[] trueDamage;

    @OneToMany
    List<HeroCard> entrance;

    //@OneToMany
    //List<HeroCard>[] heroesInRoom;

    public Integer getTreasureAmount(TreasureType treasure) {

        Integer totalAmount = 0;
        for (RoomCard room: rooms) {
            totalAmount += room.parseTreasureAmount(treasure);
        }
        return totalAmount;
        
    }

    public void addNewHeroToDungeon(HeroCard hero) {
        entrance.add(hero);
    }

}
