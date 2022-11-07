package org.springframework.samples.bossmonster.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    StatisticsRepository repo;

    @Autowired
    public StatisticsService( StatisticsRepository r){
        this.repo=r;
    }
    public Player getStatisticsPlayer(int id){
        repo.findById(id);
        return null;
    }
    
}
