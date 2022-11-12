package org.springframework.samples.bossmonster.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    
    StatisticsRepository repo;
    UserRepository repoUser;

    @Autowired
    public StatisticsService(StatisticsRepository r, UserRepository r2){
        this.repo=r;
        this.repoUser=r2;
    }

    List<GameResult> findAll(int Id){
        return repo.findAllGameResultsUser(Id);
    }
    List<GameResult> findAllWinned(int Id){
        return repo.findAllWinnedGamesUser(Id);
    }
    Double winRate(int Id){
        Double total=repo.findAllGameResultsUser(Id).size()*1.0;
        Double wins=repo.findAllWinnedGamesUser(Id).size()*1.0;
        return wins/total;
    }
    Double averageDuration(int Id){
        List<GameResult> games= repo.findAllGameResultsUser(Id);
        Double duration= games.stream().mapToDouble(GameResult::getDuration).sum();
        return duration/games.size();
    }

}
