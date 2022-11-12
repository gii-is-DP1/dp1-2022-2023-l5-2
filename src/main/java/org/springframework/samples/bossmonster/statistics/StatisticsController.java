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

    private final String STATISTICS_VIEW="/statistics/UserStatistics";

    @Autowired
    public StatisticsController(StatisticsService s, UserService s2){
        this.service=s;
        this.service2=s2;
    }
    
    @GetMapping("/")
    public ModelAndView showUserStatistics(){
        User loggedInUser=service2.getLoggedInUser().get();
        List<GameResult> games= service.findAll(loggedInUser);
    }

}
