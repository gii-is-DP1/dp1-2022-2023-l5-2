package org.springframework.samples.bossmonster.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AchievementService {

    AchievementRepository repo;

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
/* 
    AchievementRepository repo;

    @Autowired
    public AchievementService(AchievementRepository repository){
        this.repo=repository;
    }

    List<Achievement> getAllAchievements(){
        return repo.findAll();
    }

    public Achievement getById(int Id){
        return repo.findById(Id).get();
    }
    public void deleteById(int Id){
        repo.deleteById(Id);
    }
    public void save(Achievement a){
        repo.save(a);
    }*/
}
