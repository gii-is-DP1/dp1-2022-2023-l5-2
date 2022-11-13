package org.springframework.samples.bossmonster.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.gameResult.GameResultRepository;
import org.springframework.samples.bossmonster.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    
    GameResultRepository repo;
    UserRepository repoUser;

    @Autowired
    public StatisticsService(GameResultRepository r, UserRepository r2){
        this.repo=r;
        this.repoUser=r2;
    }

    List<GameResult> findAll(String Id){
        return repo.findAllGameResultsUser(Id);
    }
    List<GameResult> findAllWinned(String Id){
        return repo.findAllWinnedGamesUser(Id);
    }
    Double winRate(String Id){
        Double total=repo.findAllGameResultsUser(Id).size()*1.0;
        Double wins=repo.findAllWinnedGamesUser(Id).size()*1.0;
        Double result= 0.;
        if(total==0.0) result=total;
        else result=wins/total;
        return result;
    }
    Double averageDuration(String Id){
        List<GameResult> games= repo.findAllGameResultsUser(Id);
        if(games.size()==0){
            return 0.;
        }else{
            Double duration= games.stream().mapToDouble(GameResult::getDuration).sum();
            return duration/games.size();
        }
    }
    List<String> findParticipants(int Id){
        List<String> partcipants= repo.findAllParticipants(Id);
        return partcipants;
    }

}
