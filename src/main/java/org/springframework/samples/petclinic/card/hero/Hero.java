package org.springframework.samples.petclinic.card.hero;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


import org.springframework.samples.petclinic.card.Card;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "heroes")
public class HeroCard extends Card{

    @Column(name = "treassure")
    @NotEmpty
    private Integer treassure;


    @Column(name = "health")
    @NotEmpty
    private Integer health;

    @Column(name = "isEpic")
    @NotEmpty
    private Boolean isEpic;

    @Column(name = "necessaryPlayers")
    @NotEmpty
    private Integer necessaryPlayers;
  
}
