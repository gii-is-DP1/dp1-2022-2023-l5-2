package org.springframework.samples.bossmonster.gameResult;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "game_result")
public class GameResult extends BaseEntity{
    
    @OneToOne
     private User winner;
     private Integer duration;

     @ManyToMany(mappedBy="results")
     private Collection<User> participants;

     @OneToOne(mappedBy = "result")
     private Game game;

}
