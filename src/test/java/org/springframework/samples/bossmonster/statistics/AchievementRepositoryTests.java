package org.springframework.samples.bossmonster.statistics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserRepository;
import org.springframework.stereotype.Repository;

import javax.validation.ValidationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class AchievementRepositoryTests {
    
    @Autowired
    AchievementRepository achievementRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldFindAllAchievements(){
        assertNotNull(achievementRepository.findAll());
    }

    @Test
    void shouldFindUserAchievements(){
        User user = userRepository.findAll().get(0);
        String username = user.getUsername();
        Achievement achievement = new Achievement();
        achievement.setName("Heroe");
        achievement.setDescription("Win five games");
        achievement.setImage("image.png");
        achievement.setMetric(Metric.VICTORIES);
        achievement.setThreshold(5);
        achievementRepository.save(achievement);
        var before = achievementRepository.findPlayerAchievements(username).size();
        Set<Achievement> setAchievement = new HashSet<>();
        setAchievement.add(achievement);
        user.setAchievements(setAchievement);
        var after = achievementRepository.findPlayerAchievements(username).size();
        assertEquals(before + 1, after);
    }


    @Test
    void shouldFindAchievementByName(){
        Achievement achievement = new Achievement();
        achievement.setName("Heroe");
        achievement.setDescription("Win five games");
        achievement.setImage("image.png");
        achievement.setMetric(Metric.VICTORIES);
        achievement.setThreshold(5);
        assertEquals(achievementRepository.findByName("Heroe"), null);
        achievementRepository.save(achievement);
        assertEquals(achievementRepository.findByName("Heroe"), achievement);
    }

    @Test
    void shouldDeleteAchievementFromUsers(){
        User user = userRepository.findAll().get(0);
        Achievement achievement = new Achievement();
        achievement.setName("Heroe");
        achievement.setDescription("Win five games");
        achievement.setImage("image.png");
        achievement.setMetric(Metric.VICTORIES);
        achievement.setThreshold(5);
        achievementRepository.save(achievement);
        Set<Achievement> setAchievement = new HashSet<>();
        setAchievement.add(achievement);
        user.setAchievements(setAchievement);
        var achievementId = achievement.getId();
        var userId = user.getUsername();
        achievementRepository.deleteAchievementFromUsers(achievementId);
        List<Achievement> listAchievement = new ArrayList<>();
        listAchievement.addAll(achievementRepository.findPlayerAchievements(userId));
        assertEquals(listAchievement, new ArrayList<Achievement>());
    }

}