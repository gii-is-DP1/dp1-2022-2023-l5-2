package org.springframework.samples.bossmonster.social;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users/friends")
public class FriendRequestController {

    private FriendRequestService serviceFR;
    private UserService serviceU;
    
    private static final String FRIEND_POP_UP_VIEW="/friends/friendsModulePopUp";
    private static final String CREATE_REQUEST="/friends/createFriendRequest";
    private static final String NOT_ACCEPTED_REQUESTS="/friends/notAcceptedFriendRequests";

    @Autowired
    public FriendRequestController(FriendRequestService sFR, UserService sU){
        this.serviceFR=sFR;
        this.serviceU=sU;
    }

    @GetMapping("/")
    public ModelAndView showFriendRequestData(){
        ModelAndView result= new ModelAndView(FRIEND_POP_UP_VIEW);
        User loggedInUser= serviceU.getLoggedInUser().get();
        String username= loggedInUser.getUsername();
        List<FriendRequest> received= serviceFR.findAllReceived(username);
        List<FriendRequest> requests= serviceFR.findAllRequested(username);
        List<User> userFriends= serviceFR.calculateFriends(username);
        
        result.addObject("received", received);
        result.addObject("requested", requests);
        result.addObject("users", userFriends);

        return result;
    }

    @GetMapping("/new")
    public String addFriendForm(Map<String, Object> model){
        FriendRequest newFriendRequest= new FriendRequest();
        User loggedInUser= serviceU.getLoggedInUser().get();
        newFriendRequest.setRequester(loggedInUser);
        model.put("friendRequest",newFriendRequest);
        return CREATE_REQUEST;
    }

    @Transactional
    @PostMapping("/new")
    public ModelAndView processFriendForm(@Valid FriendRequest request, BindingResult br){
        ModelAndView result=new ModelAndView();
        if (br.hasErrors()) {
			result = new ModelAndView(CREATE_REQUEST);
			result.addObject("message", "Can't create request. Invalid values are present");
		}
        else if (serviceFR.checkAlreadyFriends(request.getRequester().getUsername(), request.getReceiver().getUsername())) {
			result = new ModelAndView(CREATE_REQUEST);
			result.addObject("message", "You are already friends with this user");
		}
        else if(serviceFR.checkAlreadySendOne(request.getRequester().getUsername(), request.getReceiver().getUsername())){
            result = new ModelAndView(CREATE_REQUEST);
			result.addObject("message", "You are already send a friend request to this user");
        }
        else {
            serviceFR.saveFriendRequest(request);
            result.setViewName("redirect:/users/friends/");
            result.addObject("message", "Friend succesfully added!");
		}
        return result;
    }
    @Transactional
    @GetMapping("/{username}/delete")
    public ModelAndView deleteFriend(@PathVariable String username){
        serviceFR.unFriendSomeone(username);
        ModelAndView result= new ModelAndView("redirect:/users/friends/");
        return result;
    }
    @GetMapping("/notAccepted")
    public ModelAndView showNotAccepted(){
        ModelAndView result= new ModelAndView(NOT_ACCEPTED_REQUESTS);
        User user= serviceU.getLoggedInUser().get();
        List<FriendRequest> notAcceptedRequests= serviceFR.notAcceptedRequests(user.getUsername());
        result.addObject("notAcceptedRequests", notAcceptedRequests);
        return result;
    }
    @Transactional
    @GetMapping("/notAccepted/{username}")
    public ModelAndView acceptRequest(@PathVariable String username){
        ModelAndView result=new ModelAndView("redirect:/users/friends/");
        serviceFR.acceptRequest(username);
        return result;
    }
    @Transactional
    @GetMapping("/notAccepted/{username}/delete")
    public ModelAndView unfriendFriend(@PathVariable String username){
        serviceFR.declineFriendRequest(username);
        ModelAndView result= new ModelAndView("redirect:/users/friends/");
        return result;
    }

    
}
