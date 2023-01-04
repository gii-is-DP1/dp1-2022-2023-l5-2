package org.springframework.samples.bossmonster.statistics;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    
    @Test
    @WithMockUser(username = "admin1")
    void testShowGlobalStatistics() throws Exception{

        when(service1.numPartidasGlobal()).thenReturn(10);
        when(service1.maxMinUsuarioPartidasGlobal(false)).thenReturn(1);
        when(service1.maxMinUsuarioPartidasGlobal(true)).thenReturn(5);
        when(service1.promedioNumPartidas()).thenReturn(2.);
        when(service1.promedioDuracionGlobal()).thenReturn(30.33);
        when(service1.maxMinDuracionGlobal(false)).thenReturn(3.);
        when(service1.maxMinDuracionGlobal(true)).thenReturn(100.);
        when(service1.promedioJugadoresPartida()).thenReturn(2.4);


        mock.perform(get("/users/statistics/global")).andExpect(status().isOk())
        .andExpect(view().name("/statistics/GlobalStatistics"))
        .andExpect(model().attribute("minDuracion",3.));
    }

    @Test
    @WithMockUser(username = "jessolort")
    void testRankingWinrate() throws Exception {
        
        Map<String, Double> map = new HashMap<>();
        map.put("juan", 2.3);
        map.put("pepe", 1.2);
        map.put("antonio", 3.0);
        List<Map.Entry<String,Double>>ranking= new ArrayList<>();
        ranking.addAll(map.entrySet());

        when(service1.rankingPorWinRate()).thenReturn(ranking);

        mock.perform(get("/users/statistics/rankings/winRate")).andExpect(status().isOk())
        .andExpect(view().name("/statistics/rankingWinRate"))
        .andExpect(model().attribute("ranking", ranking));
    }

    @Test
    @WithMockUser(username = "jessolort")
    void testRankingWins() throws Exception {
        
        Map<String, Integer> map = new HashMap<>();
        map.put("juan", 2);
        map.put("pepe", 1);
        map.put("antonio", 3);
        List<Map.Entry<String,Integer>>ranking= new ArrayList<>();
        ranking.addAll(map.entrySet());

        when(service1.rankingPorWins()).thenReturn(ranking);

        mock.perform(get("/users/statistics/rankings/wins")).andExpect(status().isOk())
        .andExpect(view().name("/statistics/rankingWins"))
        .andExpect(model().attribute("ranking", ranking));
    }

    @Test
    @WithMockUser(username = "admin1")
    void testShowPlayedGames() throws Exception {
        
        GameResult game1=new GameResult();
        GameResult game2= new GameResult();
        GameResult game3= new GameResult();
        List<GameResult> games= List.of(game1,game2,game3);

        when(service1.findAllGames()).thenReturn(games);

        mock.perform(get("/statistics/listPlayedGames")).andExpect(status().isOk())
        .andExpect(view().name("/statistics/playedGames"))
        .andExpect(model().attribute("playedGames", games));
    }

    @Test
    @WithMockUser(username = "admin1")
    void testShowResults() throws Exception {
        
        Integer resultId = 1;
        Optional<GameResult> gameResult= Optional.ofNullable(new GameResult());
        when(service1.findById(resultId)).thenReturn(gameResult);

        mock.perform(get("/statistics/results/1")).andExpect(status().isOk())
        .andExpect(view().name("statistics/resultsScreen"))
        .andExpect(model().attribute("result", gameResult.get()));
    }

}
