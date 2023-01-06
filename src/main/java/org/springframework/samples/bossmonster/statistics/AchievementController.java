package org.springframework.samples.bossmonster.statistics;


import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/statistics/achievements")
public class AchievementController {
    private final String PERSONAL_LISTING_VIEW="/achievements/personalAchievementsListing";
    private final String ACHIEVEMENTS_LISTING_VIEW="/achievements/AchievementsListing";
    private final String ACHIEVEMENTS_FORM="/achievements/createOrUpdateAchievementForm";
    private final String USER_ACHIEVEMENTS_FORM="/achievements/createOrUpdateAchievementsOfUserForm";

    private AchievementService achievementService;
    private UserService userService;

    @Autowired
    public AchievementController(AchievementService achievementService, UserService userService){
        this.achievementService=achievementService;
        this.userService=userService;
    }

    @Transactional(readOnly = true)
    @GetMapping("")
    public ModelAndView showAchievements(){
        ModelAndView result=new ModelAndView(ACHIEVEMENTS_LISTING_VIEW);
        result.addObject("achievements", achievementService.getAchievements());
        return result;
    }

    @Transactional()
    @GetMapping("/{id}/delete")
    public ModelAndView deleteAchievement(@PathVariable int id){
        achievementService.deleteAchievementById(id);        
        return showAchievements();
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}/edit")
    public ModelAndView editAchievement(@PathVariable int id){
        Achievement achievement=achievementService.getById(id);        
        ModelAndView result=new ModelAndView(ACHIEVEMENTS_FORM);
        result.addObject("achievement", achievement);         
        result.addObject("metrics",Arrays.asList(Metric.values()));        
        return result;
    }
 
    @Transactional
    @PostMapping("/{id}/edit")
    public ModelAndView saveAchievement(@PathVariable int id,@Valid Achievement achievement, BindingResult br){
        ModelAndView result=null;
        if(br.hasErrors()){
            result=new ModelAndView(ACHIEVEMENTS_FORM,br.getModel());            
            result.addObject("metrics",Arrays.asList(Metric.values()));        
            return result;
        }
        Achievement achievementToBeUpdated=achievementService.getById(id);
        BeanUtils.copyProperties(achievement,achievementToBeUpdated,"id");
        achievementService.save(achievementToBeUpdated);
        result=showAchievements();
        result.addObject("message", "The achievement was updated successfully");
        return result;        
    }

    @Transactional(readOnly = true)
    @GetMapping("/new")
    public ModelAndView createAchievement(){
        Achievement achievement=new Achievement();
        ModelAndView result=new ModelAndView(ACHIEVEMENTS_FORM);        
        result.addObject("achievement",achievement);
        result.addObject("metrics",Arrays.asList(Metric.values()));        
        return result;
    }

    @Transactional
    @PostMapping("/new")
    public ModelAndView saveNewAchievement(@Valid Achievement achievement, BindingResult br){
        ModelAndView result=null;
        if(br.hasErrors()){
            result=new ModelAndView(ACHIEVEMENTS_FORM,br.getModel());            
            result.addObject("metrics",Arrays.asList(Metric.values()));        
            return result;
        }
        achievementService.save(achievement);
        result=showAchievements();
        result.addObject("message", "The achievement was created successfully");
        return result;
    }

    @GetMapping("/byUser/{username}")
    public ModelAndView showPersonalAchievementsListing(@PathVariable String username){
        ModelAndView result=new ModelAndView(PERSONAL_LISTING_VIEW);
        result.addObject("achievements",achievementService.getAchievementsByUser(username));
        result.addObject("availableAchievements",achievementService.getAchievements());
        return result;
    }

    @GetMapping("/byUser/{username}/edit")
    public ModelAndView showUserAchievementsEditForm(@PathVariable("username")String username){
        ModelAndView result=new ModelAndView(USER_ACHIEVEMENTS_FORM);
        User user=userService.findUser(username).get();
        result.addObject("user", user);
        result.addObject("availableAchievements",achievementService.getAchievements());
        return result;
    }

    @PostMapping("/byUser/{username}/edit")
    public ModelAndView updateUserAchievements(User user,BindingResult brresult,@PathVariable("username")String username){        
        User userToBeUpdated=userService.findUser(username).get();
        userToBeUpdated.getAchievements().clear();
        userToBeUpdated.getAchievements().addAll(user.getAchievements());
        userService.saveUser(userToBeUpdated);
        return showPersonalAchievementsListing(username);
    }

    @GetMapping("/me")
    public ModelAndView showCurrentUserAchievements(@AuthenticationPrincipal Object loged){
        ModelAndView result=null;
        User user=null;
        if(loged!=null && (loged instanceof UserDetails))
            user=userService.findUser(((UserDetails)loged).getUsername()).get();
        if(user!=null){
            result = showPersonalAchievementsListing(user.getUsername());
        }else{
            result=new ModelAndView("welcome");
            result.addObject("message","You are not an User, thus you don't have achievements");
            result.addObject("messageType","warning");            
        }
        return result;
    }

}

