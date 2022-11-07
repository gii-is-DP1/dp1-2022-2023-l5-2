package org.springframework.samples.bossmonster.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    StatisticsRepository service;
    
    private final String STATISTICS_LISTING_VIEW="/StatisticsScreen";

    @Autowired
    public StatisticsController(StatisticsRepository s){
        this.service=s;
    }
    @GetMapping("/")
    public ModelAndView showAchievement(){
        ModelAndView result= new ModelAndView(STATISTICS_LISTING_VIEW);
        return result;
    }

}
