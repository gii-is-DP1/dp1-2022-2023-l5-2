package org.springframework.samples.bossmonster.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.configuration.SecurityConfiguration;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(
    controllers = AchievementController.class,
    excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class
    )
public class AchievementControllerTests {
    
    @MockBean
    private AchievementService achievementService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private Achievement testAchievement;

    @BeforeEach
    void setUp(){
        testAchievement = new Achievement();
        testAchievement.setId(1);
        testAchievement.setName("Heroe");
        testAchievement.setDescription("Win five games");
        testAchievement.setImage("image.png");
        testAchievement.setMetric(Metric.VICTORIES);
        testAchievement.setThreshold(5);
        
        User user= new User();
        user.setUsername("ignarrman");

        when(achievementService.getById(anyInt())).thenReturn(testAchievement);
        when(userService.getLoggedInUser()).thenReturn(Optional.of(user));
    }

    @Test
    @WithMockUser
    public void testShowAchievements() throws Exception{
        mockMvc.perform(get("/statistics/achievements"))
            .andExpect(status().isOk())
            .andExpect(view().name("/achievements/AchievementsListing"));
    }

    @Test
    @WithMockUser(value = "testAchievement")
    public void testDeleteAchievement() throws Exception{
        mockMvc.perform(get("/statistics/achievements/1/delete"))
            .andExpect(status().isOk());
        verify(achievementService).deleteAchievementById(1);
    }

    @Test
    @WithMockUser(value = "testAchievement")
    public void testEditAchievement() throws Exception{
        mockMvc.perform(get("/statistics/achievements/1/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("/achievements/createOrUpdateAchievementForm"))
            .andExpect(model().attribute("achievement", hasProperty("description", is("Win five games"))))
            .andExpect(model().attribute("achievement", hasProperty("image", is("image.png"))))
            .andExpect(model().attribute("achievement", hasProperty("metric", is(Metric.VICTORIES))))
            .andExpect(model().attribute("achievement", hasProperty("threshold", is(5))));
    }
    
    @Test
    @WithMockUser(value = "testAchievement")
    public void testSaveAchievement() throws Exception{
        mockMvc.perform(post("/statistics/achievements/1/edit").with(csrf())
            .param("name", "Villain")
            .param("description", "Win ten games")
            .param("image", "image2.png")
            .param("metric", "VICTORIES")
            .param("threshold", "10"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testAchievement")
    public void testSaveAchievementHasErrors() throws Exception{
        mockMvc.perform(post("/statistics/achievements/1/edit").with(csrf())
            .param("name", "")
            .param("description", "Win ten games")
            .param("image", "image2.png")
            .param("metric", "ANY")
            .param("threshold", "-10"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("achievement"))
            .andExpect(model().attributeHasFieldErrors("achievement", "name"))
            .andExpect(model().attributeHasFieldErrors("achievement", "metric"))
            .andExpect(model().attributeHasFieldErrors("achievement", "threshold"))
            .andExpect(model().attributeHasFieldErrors("achievement", "name"))
            .andExpect(view().name("/achievements/createOrUpdateAchievementForm"));
    }

    @Test
    @WithMockUser
    public void testCreateAchievement() throws Exception{
        mockMvc.perform(get("/statistics/achievements/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("/achievements/createOrUpdateAchievementForm"));
    }

    @Test
    @WithMockUser
    public void testSaveNewAchievement() throws Exception{
        mockMvc.perform(post("/statistics/achievements/new").with(csrf())
            .param("name", "Villain")
            .param("description", "Win ten games")
            .param("image", "image2.png")
            .param("metric", "VICTORIES")
            .param("threshold", "10"))
            .andExpect(status().isOk())
            .andExpect(view().name("/achievements/AchievementsListing"));
    }

    @Test
    @WithMockUser(value = "testAchievement")
    public void testSaveNewAchievementHasErrors() throws Exception{
        mockMvc.perform(post("/statistics/achievements/new").with(csrf())
            .param("name", "")
            .param("description", "Win ten games")
            .param("image", "image2.png")
            .param("metric", "ANY")
            .param("threshold", "-10"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("achievement"))
            .andExpect(model().attributeHasFieldErrors("achievement", "name"))
            .andExpect(model().attributeHasFieldErrors("achievement", "metric"))
            .andExpect(model().attributeHasFieldErrors("achievement", "threshold"));
    }

    @Test
    @WithMockUser(value = "ignarrman",username = "ignarrman")
    public void testShowCurrentUserAchievements() throws Exception{
        mockMvc.perform(get("/statistics/achievements/me"))
        .andExpect(status().isOk());
    }

}
