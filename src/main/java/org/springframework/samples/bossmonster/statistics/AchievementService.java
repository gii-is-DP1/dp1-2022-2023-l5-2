package org.springframework.samples.bossmonster.statistics;

import java.util.List;
import java.util.Set;

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
    public AchievementService(AchievementRepository repo){
        this.repo=repo;
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

    public void triggerAchievement(User user){
        List<Achievement> achievements = getAchievements();
        List<Achievement> achievementUser = getAchievementsByUser(user.getUsername());
        achievements.removeAll(achievementUser);
        for(int i = 0; i < achievements.size(); i++){
            Achievement achievement = achievements.get(i);
            switch(achievement.getMetric()){
                case GAMES_PLAYED:
                    Integer games_played = statisticsService.findAll(user.getUsername()).size();
                    if(achievement.getThreshold() <= games_played){
                        unlockAchievement(user, achievement);
                    }
                    break;
                case VICTORIES:
                    Integer victories = statisticsService.findAllWinned(user.getUsername()).size();
                    if(achievement.getThreshold() <= victories){
                        unlockAchievement(user, achievement);
                    }
                    break;
                case TOTAL_PLAY_TIME:
                    Double playTime = totalHoursPlayed(user.getUsername());
                    if(achievement.getThreshold() <= playTime){
                        unlockAchievement(user, achievement);
                    }
                    break;
            }
        }
    }

    private void unlockAchievement(User user, Achievement achievement) {
        Set<Achievement> achievementsAlreadyUnlocked = user.getAchievements();
        if(!achievementsAlreadyUnlocked.contains(achievement)){
            achievementsAlreadyUnlocked.add(achievement);
            user.setAchievements(achievementsAlreadyUnlocked);
            userService.saveUser(user);
        }
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
