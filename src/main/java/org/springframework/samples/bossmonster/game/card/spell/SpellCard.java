package org.springframework.samples.bossmonster.game.card.spell;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.EffectEnum;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


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