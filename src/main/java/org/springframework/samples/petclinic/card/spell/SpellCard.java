package org.springframework.samples.petclinic.card.spell;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.Card;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Getter
@Setter
@Table(name = "spell_card")
public class SpellCard extends Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "phase")
    @NotEmpty
    @Enumerated(EnumType.STRING)
    SpellPhase phase;

    @NotEmpty
    String effect;


}
