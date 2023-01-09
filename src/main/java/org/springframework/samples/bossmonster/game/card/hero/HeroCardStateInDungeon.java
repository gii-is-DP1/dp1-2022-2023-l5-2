package org.springframework.samples.bossmonster.game.card.hero;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.NoArgsConstructor;
import org.springframework.samples.bossmonster.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class HeroCardStateInDungeon extends BaseEntity {

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

    public HeroCardStateInDungeon(HeroCard hero) {
        this.heroCard = hero;
        this.healthInDungeon = hero.getHealth();
        this.minotaursMazeEffectTriggered = false;
    }


}
