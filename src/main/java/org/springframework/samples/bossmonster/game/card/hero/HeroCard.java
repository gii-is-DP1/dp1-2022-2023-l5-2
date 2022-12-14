package org.springframework.samples.bossmonster.game.card.hero;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.TreasureType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "heroes")
public class HeroCard extends Card{

    @Column(name = "health")
    @NotEmpty
    private Integer health;

    @Column(name = "is_epic")
    @NotEmpty
    private Boolean isEpic;

    @Column(name = "treasure")
    @Enumerated(EnumType.STRING)
    @NotEmpty
    private TreasureType treasure;

    @Column(name = "necessary_players")
    @NotEmpty
    private Integer necessaryPlayers;

}