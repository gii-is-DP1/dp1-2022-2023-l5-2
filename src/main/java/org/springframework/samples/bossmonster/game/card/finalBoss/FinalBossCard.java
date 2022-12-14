package org.springframework.samples.bossmonster.game.card.finalBoss;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.TreasureType;

import javax.persistence.Column;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;

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

}