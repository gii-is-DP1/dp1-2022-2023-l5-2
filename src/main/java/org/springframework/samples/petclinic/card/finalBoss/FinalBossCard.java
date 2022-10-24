package org.springframework.samples.petclinic.card.finalBoss;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.Card;

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
