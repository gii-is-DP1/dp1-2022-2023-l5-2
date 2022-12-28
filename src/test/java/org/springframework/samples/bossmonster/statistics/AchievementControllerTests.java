package org.springframework.samples.bossmonster.statistics;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.configuration.SecurityConfiguration;
import org.springframework.samples.bossmonster.statistics.Achievement;
import org.springframework.samples.bossmonster.statistics.AchievementController;
import org.springframework.samples.bossmonster.statistics.AchievementService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;


@WebMvcTest(
    controllers = AchievementController.class,
    excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class
    )
@ExtendWith(MockitoExtension.class)
public class AchievementControllerTests {
    
    @MockBean
    private AchievementService achievementService;

    @Autowired
    private MockMvc mockMvc;
    
    private Achievement testAchievement;
    private List<Achievement> testAchievementsList;

    @BeforeEach
    void setUp(){
        testAchievement.setName("Test Achievement");
        testAchievement = new Achievement();
        testAchievement.setDescription("Test description");
        testAchievement.setImage("Test image");
        testAchievement.setThreshold(47);
        testAchievement.setMetric(Metric.GAMES_PLAYED);
        testAchievementsList.add(testAchievement);
        given(this.achievementService.getAchievements()).willReturn(testAchievementsList);
    }

    @WithMockUser(value = "spring")
    @Test
    public void testShowAchievements() throws Exception{
        mockMvc.perform(get("/statistics/achievements"))
        .andExpect(status().isOk());
    }

}
