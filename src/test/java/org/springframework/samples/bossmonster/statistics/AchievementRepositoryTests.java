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
import java.util.List;

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

    /*

    @Test
    void shouldFindUserAchievements(){
        User user = userRepository.findAll().get(0);
        Achievement achievement = new Achievement();
        achievement.setName("Heroe");
        achievement.setDescription("Win five games");
        achievement.setImage("image.png");
        achievement.setMetric(Metric.VICTORIES);
        achievement.setThreshold(5);
        achievementRepository.
    }

    */

    @Test
    void shouldFindAchievementByName(){
        
    }

    @Test
    void shouldDeleteAchievementFromUsers(){

    }

}