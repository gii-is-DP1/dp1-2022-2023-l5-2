package org.springframework.samples.bossmonster.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AchievementService {

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
    }
}
