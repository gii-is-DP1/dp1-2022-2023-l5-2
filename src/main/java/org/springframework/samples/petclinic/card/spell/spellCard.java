package org.springframework.samples.petclinic.card.spell;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


import org.springframework.samples.petclinic.card.Card;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "spell_card")
public class spellCard extends Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "phase")
    @NotEmpty
    private

    @NotEmpty
    private String effect;


}