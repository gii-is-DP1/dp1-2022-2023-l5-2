package org.springframework.samples.bossmonster.social;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class FriendRequestController {

    private FriendRequestService serviceFR;
    private UserService serviceU;
    
    private static final String FRIEND_POP_UP_VIEW="/friends/friendsModulePopUp";

    @Autowired
    public FriendRequestController(FriendRequestService sFR, UserService sU){
        this.serviceFR=sFR;
        this.serviceU=sU;
    }

    @GetMapping("/users/friends")
    public ModelAndView showFriendRequestData(){
        ModelAndView result= new ModelAndView(FRIEND_POP_UP_VIEW);
        User loggedInUser= serviceU.getLoggedInUser().get();
        String username= loggedInUser.getUsername();
        List<FriendRequest> received= serviceFR.findAllReceived(username);
        List<FriendRequest> requests= serviceFR.findAllRequested(username);
        List<User> friends= serviceFR.calculateFriends(username);
        
        result.addObject("received", received);
        result.addObject("requested", requests);
        result.addObject("friends", friends);

        return result;
    }

    
}
