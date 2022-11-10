package org.springframework.samples.bossmonster.gameResult;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.user.User;

import ch.qos.logback.core.util.Duration;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GameResult {
     private User winner;
     private List<User> participants;
     private Duration duration;

     @ManyToMany(mappedBy="results")
     private List<User> users;

     @OneToOne(mappedBy = "result")
     private Game game;

}
