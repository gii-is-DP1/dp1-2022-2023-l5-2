package org.springframework.samples.petclinic.card.hero;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.card.Card;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HeroCard extends Card {
    private Integer health;
    private Boolean isFinal;
    private Integer playersNecessary;
}
