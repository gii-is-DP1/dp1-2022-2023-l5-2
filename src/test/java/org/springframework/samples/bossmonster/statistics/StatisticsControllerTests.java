package org.springframework.samples.bossmonster.statistics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.configuration.SecurityConfiguration;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;



@WebMvcTest(controllers = StatisticsController.class,
excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
@ExtendWith(MockitoExtension.class)

public class StatisticsControllerTests {

    @MockBean
    private StatisticsService service1;

    @MockBean
    private UserService service2;

    @Autowired
    MockMvc mock;

    @Test
    @WithMockUser(username = "ignarrman")
    void testShowStatisticsUser() throws Exception{
        
        User user= new User();
        user.setUsername("ignarrman");
        GameResult game1=new GameResult();
        GameResult game2= new GameResult();
        List<GameResult> games= List.of(game1,game2);
        List<GameResult> winned= List.of(game1);

        when(service2.getLoggedInUser()).thenReturn(Optional.ofNullable(user));
        when(service1.findAll(anyString())).thenReturn(games);
        when(service1.findAllWinned(anyString())).thenReturn(winned);
        when(service1.winRate(anyString())).thenReturn(50.);
        when(service1.averageDuration(anyString())).thenReturn(2.33);
        when(service1.winStreakUser(anyList(), anyString())).thenReturn(1);

        mock.perform(get("/users/statistics")).andExpect(status().isOk())
        .andExpect(view().name("/statistics/UserStatistics"))
        .andExpect(model().attribute("total",2));
    }
    
}
