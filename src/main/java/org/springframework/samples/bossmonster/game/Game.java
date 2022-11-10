package org.springframework.samples.bossmonster.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends BaseEntity{

    @OneToMany
    private Collection<Player> players;

    private Date date;
    private Duration duration;
    private String winnerName;
    private String finalSouls; //formato 5/6/8/10
    private String finalHealth; //formato 0/0/0/2

    @OneToOne
    private GameResult result;

}
