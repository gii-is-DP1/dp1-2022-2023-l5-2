package org.springframework.samples.bossmonster.game.dungeon;

import java.util.List;

import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dungeon {
    
    FinalBossCard bossCard;

    RoomCard[] rooms;
    List<HeroCard>[] heroesInRoom;

}
