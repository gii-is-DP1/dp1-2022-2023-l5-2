package org.springframework.samples.bossmonster.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("users/statistics")
public class StatisticsController {
    
    StatisticsService service;
    UserService service2;

    private static final String STATISTICS_VIEW="/statistics/UserStatistics";

    @Autowired
    public StatisticsController(StatisticsService s, UserService s2){
        this.service=s;
        this.service2=s2;
    }
    
    @GetMapping("/")
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

}
