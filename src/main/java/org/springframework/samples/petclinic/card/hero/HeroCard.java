package org.springframework.samples.petclinic.card.hero;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HeroCard {
    private Integer health;
    private Boolean isFinal;
    private Integer playersNecessary;
}
