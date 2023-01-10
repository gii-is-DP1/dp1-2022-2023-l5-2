package org.springframework.samples.bossmonster.invitations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class InvitationController {

    private InvitationService service;
    private UserService userService;

    private static final String INVITATIONS_VIEW="/gameLobbies/showInvitations";

    @Autowired
    public InvitationController(InvitationService service, UserService userService){
        this.service=service;
        this.userService=userService;
    }

    @GetMapping("/invites")
    public ModelAndView showInvitations(){
        ModelAndView result= new ModelAndView(INVITATIONS_VIEW);
        User user= userService.getLoggedInUser().get();
        List<Invitation> invitations= service.getInvitations(user.getUsername());

        result.addObject("invitations",invitations);
        return result;
    }
    
}
