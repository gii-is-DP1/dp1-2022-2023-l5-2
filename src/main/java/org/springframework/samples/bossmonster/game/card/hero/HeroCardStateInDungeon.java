package org.springframework.samples.bossmonster.game.card.hero;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.model.BaseEntity;

@Entity
public class HeroCardStateInDungeon extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private Dungeon dungeon;

    @OneToOne(cascade = CascadeType.ALL)
    private HeroCard heroCard;

    private Integer healthInDungeon;

    private Boolean minotaursMazeEffectTriggered;
    
}
