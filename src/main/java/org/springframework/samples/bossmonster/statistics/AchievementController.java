package org.springframework.samples.bossmonster.statistics;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/")
public class AchievementController {

    private AchievementService service;

    private final String ACHIEVEMENTS_LISTING_VIEW="/achievements/achievementsListing";
//    private final String ACHIEVEMENTS_FORM="/achievements/createOrUpdateAchievementForm";

    @Autowired
    public AchievementController(AchievementService s){
        this.service=s;
    }
 
    @GetMapping("/users/achievements")
    public ModelAndView showAchievement(){
        ModelAndView result= new ModelAndView(ACHIEVEMENTS_LISTING_VIEW);
        result.addObject("achievements", service.getAllAchievements());
        return result;
    }
/*
    @GetMapping("/{id}/delete")
    public ModelAndView deleteAchievement(@PathVariable int Id){
        service.deleteById(Id);
        // ModelAndView result= new ModelAndView(ACHIEVEMENTS_LISTING_VIEW);
        // result.addObject("message", "El logro se ha borrado con éxito");
        return showAchievement();
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editAchievement(@PathVariable int Id){
        Achievement achievement=service.getById(Id);
        ModelAndView result= new ModelAndView(ACHIEVEMENTS_FORM);
        result.addObject("achievement", achievement);
        // result.addObject("message", "El logro se ha actualizado con éxito");
        return result;
    }
    @PostMapping("/{id}/edit")
    public ModelAndView saveAchievement(@PathVariable int id, Achievement achievement){
        Achievement achievementToBeUpdated= service.getById(id);
        BeanUtils.copyProperties(achievement, achievementToBeUpdated,"id");
        service.save(achievementToBeUpdated);
        return showAchievement();
    }
*/

}

