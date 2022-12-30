package org.springframework.samples.bossmonster.game.card.hero;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class HeroCardStateInDungeon extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private Dungeon dungeon;

    @OneToOne()
    private HeroCard heroCard;

    private Integer healthInDungeon;

    private Boolean minotaursMazeEffectTriggered;

    public void dealDamage(Integer amount) {
        healthInDungeon -= amount;
    }

    public Boolean isDead() {
        return healthInDungeon <= 0;
    }
    
}
