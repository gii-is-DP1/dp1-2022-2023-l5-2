package org.springframework.samples.bossmonster.statistics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Service;



@Service
public class AchievementService {

    AchievementRepository repo;
    StatisticsService statisticsService;
    UserService userService;

@Autowired
public AchievementService(AchievementRepository repo, StatisticsService statisticsService, UserService userService){
    this.repo=repo;
    this.statisticsService=statisticsService;
    this.userService=userService;
}

    List<Achievement> getAchievements(){
        return repo.findAll();
    }

    public Achievement getById(int id){
        return repo.findById(id).get();
    }

    public void deleteAchievementById(int id){
        repo.deleteAchievementFromUsers(id);
        repo.deleteById(id);
    }

    public void save(Achievement achievement){
        repo.save(achievement);
    }

    public List<Achievement>  getAchievementsByUser(String username) {
        return repo.findPlayerAchievements(username);
    }

    public Achievement getAchievementByName(String name){
        return repo.findByName(name);
    }

    public List<Achievement> triggerAchievement(User user){
        String username = user.getUsername();
        List<Achievement> achievements = getAchievements();
        List<Achievement> result = new ArrayList<>();
        for(int i = 0; i < achievements.size(); i++){
            Achievement achievement = achievements.get(i);
            switch(achievement.getMetric()){
                case GAMES_PLAYED:
                    Integer games_played = statisticsService.findAll(username).size();
                    if(achievement.getThreshold() <= games_played){
                        result.add(achievement);
                    }
                    break;
                case VICTORIES:
                    Integer victories = statisticsService.findAllWinned(user.getUsername()).size();
                    if(achievement.getThreshold() <= victories){
                        result.add(achievement);
                    }
                    break;
                case TOTAL_PLAY_TIME:
                    Double playTime = totalHoursPlayed(user.getUsername());
                    if(achievement.getThreshold() <= playTime){
                        result.add(achievement);
                    }
                    break;
            }
        }
        return result;

    }

    private Double totalHoursPlayed(String username) {
        List<GameResult> games= statisticsService.findAll(username);
        if(games.size()==0){
            return 0.;
        }else{
            return games.stream().mapToDouble(GameResult::getMinutes).sum();
        }
    }
}
