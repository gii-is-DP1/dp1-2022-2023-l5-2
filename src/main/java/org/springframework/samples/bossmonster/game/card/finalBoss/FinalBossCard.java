package org.springframework.samples.bossmonster.game.card.finalBoss;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.game.card.Card;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "bosses")
public class FinalBossCard extends Card {

    private Integer xp;
    private String treasure;

}
