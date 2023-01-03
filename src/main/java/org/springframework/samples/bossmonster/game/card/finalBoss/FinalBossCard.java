package org.springframework.samples.bossmonster.game.card.finalBoss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.EffectEnum;
import org.springframework.samples.bossmonster.game.card.TreasureType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bosses")
public class FinalBossCard extends Card {

    private Integer xp;

    @Column(name = "treasure")
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private TreasureType treasure;

    @Column(name="effect")
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private EffectEnum effect;

}