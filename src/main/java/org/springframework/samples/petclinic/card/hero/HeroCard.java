package org.springframework.samples.petclinic.card.hero;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;


import org.springframework.samples.petclinic.card.Card;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@Table(name = "herocards")
public class HeroCard extends Card{

    @Column(name = "health")
    @NotEmpty
    private Integer health;

    @Column(name = "isFinal")
    @NotEmpty
    private Boolean isFinal;

    @Column(name = "necessaryPlayers")
    @NotEmpty
    private Integer necessaryPlayers;

    public HeroCard() {
    }

    public HeroCard(@NotEmpty Integer health, @NotEmpty Boolean isFinal, @NotEmpty Integer necessaryPlayers) {
        this.health = health;
        this.isFinal = isFinal;
        this.necessaryPlayers = necessaryPlayers;
    }

    
}
