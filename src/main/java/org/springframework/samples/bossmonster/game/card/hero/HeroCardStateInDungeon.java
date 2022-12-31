package org.springframework.samples.bossmonster.game.card.hero;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.NoArgsConstructor;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class HeroCardStateInDungeon extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
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

    public HeroCardStateInDungeon(HeroCard hero, Dungeon dungeon) {
        this.heroCard = hero;
        this.dungeon = dungeon;
        this.healthInDungeon = hero.getHealth();
        this.minotaursMazeEffectTriggered = false;
    }


}
