package org.springframework.samples.bossmonster.statistics;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class StatisticsController {
    
    StatisticsService service;
    UserService service2;

    private static final String STATISTICS_VIEW="/statistics/UserStatistics";
    private static final String GLOBAL_STATISTICS_VIEW="/statistics/GlobalStatistics";
    private static final String RANKING_WINRATE_VIEW="/statistics/rankingWinRate";

    @Autowired
    public StatisticsController(StatisticsService s, UserService s2){
        this.service=s;
        this.service2=s2;
    }
    
    @GetMapping("/users/statistics")
    public ModelAndView showUserStatistics(){
        ModelAndView result= new ModelAndView(STATISTICS_VIEW);
        User loggedInUser=service2.getLoggedInUser().get();
        String username= loggedInUser.getUsername();
        List<GameResult> games= service.findAll(username);
        Integer total= games.size();
        Integer winned= service.findAllWinned(username).size();
        Double winRate =service.winRate(username);
        Double averageDuration= service.averageDuration(username);
        Integer winStreak=service.winStreakUser(games, username);

        result.addObject("user", loggedInUser);
        result.addObject("results", games);
        result.addObject("total", total);
        result.addObject("winned", winned);
        result.addObject("winRate", winRate);
        result.addObject("averageDuration", averageDuration);
        result.addObject("winStreak", winStreak);
        result.addObject("gamesResult", games);
        
        return result;

    }
    @GetMapping("/users/statistics/global")
    public ModelAndView showGlobalStatistics(){
        ModelAndView result= new ModelAndView(GLOBAL_STATISTICS_VIEW);

        Integer totalGames=service.numPartidasGlobal();
        Integer minPartidas=service.maxMinUsuarioPartidasGlobal(false);
        Integer maxPartidas=service.maxMinUsuarioPartidasGlobal(true);
        Double promedioNumPartidas=service.promedioNumPartidas();

        Double promedioDuracion=service.promedioDuracionGlobal();
        Double minDuracion=service.maxMinDuracionGlobal(false);
        Double maxDuracion=service.maxMinDuracionGlobal(true);

        Double promedioJugadoresPartida=service.promedioJugadoresPartida();

        result.addObject("totalGames", totalGames);
        result.addObject("minPartidas", minPartidas);
        result.addObject("maxPartidas", maxPartidas);
        result.addObject("promedioNumPartidas", promedioNumPartidas);
        result.addObject("promedioDuracion", promedioDuracion);
        result.addObject("minDuracion", minDuracion);
        result.addObject("maxDuracion", maxDuracion);
        result.addObject("promedioJugadoresPartida", promedioJugadoresPartida);

        return result;
    }
    @GetMapping("/users/statistics/rankings/winRate")
    public ModelAndView rankingWinrate(){
        ModelAndView result= new ModelAndView(RANKING_WINRATE_VIEW);
        List<Map.Entry<String,Double>>ranking= service.rankingPorWinRate();
        result.addObject("ranking", ranking);
        return result;
    }

}
