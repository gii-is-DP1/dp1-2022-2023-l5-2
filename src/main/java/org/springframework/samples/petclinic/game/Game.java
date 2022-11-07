package org.springframework.samples.petclinic.game;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends BaseEntity{

    @ManyToMany(mappedBy = "games")
    //Cambiar el String por la clase Player cuando este hecha
    private Collection<Player> players;
    
    private Date date;
    private Duration duration;
    private String winnerName;
    private String Souls;
    private String Health; 
    
}
