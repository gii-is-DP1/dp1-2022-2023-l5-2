package org.springframework.samples.bossmonster.social;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class FriendRequestController {

    private FriendRequestService serviceFR;
    private UserService serviceU;
    
    private static final String FRIEND_POP_UP_VIEW="/friends/friendsModulePopUp";
    private static final String CREATE_REQUEST="/friends/createFriendRequest";

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

    @GetMapping("/users/friends/new")
    public String addFriendForm(Map<String, Object> model){
        FriendRequest newFriendRequest= new FriendRequest();
        model.put("friendRequest",newFriendRequest);
        return CREATE_REQUEST;
    }

    @Transactional
    @PostMapping("/users/friends/new")
    public ModelAndView processFriendForm(@Valid FriendRequest request, BindingResult br){
        ModelAndView result;
        if (br.hasErrors()) {
			result = new ModelAndView(CREATE_REQUEST);
			result.addObject("message", "Can't create request. Invalid values are present");
		}
        else if (/*check if they are already friends */) {
			result = new ModelAndView(CREATE_REQUEST);
			result.addObject("message", "The username provided is already used");
		}
        else {
			result = new ModelAndView(FRIEND_POP_UP_VIEW);
			//serviceFR.saveUser(request);
			result.addObject("message", "User succesfully created!");
		}
    }

    
}
