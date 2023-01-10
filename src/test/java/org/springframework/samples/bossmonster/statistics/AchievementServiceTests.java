package org.springframework.samples.bossmonster.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.social.FriendRequestService;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.FilterType;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class),
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {GameService.class,FriendRequestService.class}))
public class AchievementServiceTests {
    
    @Autowired
    AchievementService achievementService;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp(){

    }

    @Test
    void shouldGetAchievements(){
        var achievement = achievementService.getAchievements();
        assertNotNull(achievement);
    }

    @Test
    void shouldGetAchievementById(){
        var achievement = achievementService.getById(1);
        assertNotNull(achievement);
    }

    @Test
    void shouldDeleteAchievementById(){
        int previousAchievements = achievementService.getAchievements().size();
        achievementService.deleteAchievementById(1);
        int newAchievements = achievementService.getAchievements().size();
        assertEquals(previousAchievements - 1, newAchievements);
    }
   
    @Test
    void shouldSaveAchievement(){
        var achievement = new Achievement();
        achievement.setName("Heroe");
        achievement.setDescription("Win five games");
        achievement.setImage("image.png");
        achievement.setMetric(Metric.VICTORIES);
        achievement.setThreshold(5);
        int previousAchievements = achievementService.getAchievements().size();
        achievementService.save(achievement);
        int newAchievements = achievementService.getAchievements().size();
        assertEquals(previousAchievements + 1, newAchievements);
    }

    @Test
    void shouldGetAchievementByUser(){
        Achievement achievement = new Achievement();
        User testUser = userService.findAllUsers().get(0);
        String nameUser = testUser.getUsername();
        achievement.setName("Heroe");
        achievement.setDescription("Win five games");
        achievement.setImage("image.png");
        achievement.setMetric(Metric.VICTORIES);
        achievement.setThreshold(5);
        achievementService.save(achievement);
        var first = achievementService.getAchievementsByUser(nameUser).size();
        Set<Achievement> setAchievements = new HashSet<>();
        setAchievements.add(achievement);
        testUser.setAchievements(setAchievements);
        var second = achievementService.getAchievementsByUser(nameUser).size();
        assertEquals(first + 1, second);
    }

    @Test
    void shouldGetAchievementByName(){
        Achievement achievement = new Achievement();
        achievement.setName("Heroe");
        achievement.setDescription("Win five games");
        achievement.setImage("image.png");
        achievement.setMetric(Metric.VICTORIES);
        achievement.setThreshold(5);
        assertEquals(achievementService.getAchievementByName("Heroe"), null);
        achievementService.save(achievement);
        assertEquals(achievementService.getAchievementByName("Heroe"), achievement);
    }
}
