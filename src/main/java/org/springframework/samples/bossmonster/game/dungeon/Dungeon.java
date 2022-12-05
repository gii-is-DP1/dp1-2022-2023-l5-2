package org.springframework.samples.bossmonster.game.dungeon;

import java.util.List;

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

    //@OneToMany
    //List<HeroCard>[] heroesInRoom;

}
