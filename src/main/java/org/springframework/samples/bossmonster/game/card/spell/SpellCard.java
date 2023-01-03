package org.springframework.samples.bossmonster.game.card.spell;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.EffectEnum;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "spell_card")
public class SpellCard extends Card {

    @Column(name = "phase")
    @NotEmpty
    @Enumerated(EnumType.STRING)
    SpellPhase phase;


    @Column(name = "effect")
    @NotEmpty
    @Enumerated(EnumType.STRING)
    EffectEnum effect;


}