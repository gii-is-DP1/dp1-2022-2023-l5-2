package org.springframework.samples.bossmonster.invitations;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.gameLobby.GameLobbyService;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class InvitationController {

    private InvitationService service;
    private UserService userService;
    private GameLobbyService gameLobbyService;

    private static final String INVITATIONS_VIEW="/gameLobbies/showInvitations";
    private static final String SEND_INVITE_FORM="/gameLobbies/newInvite";

    @Autowired
    public InvitationController(InvitationService service, UserService userService, GameLobbyService gameLobbyService){
        this.service=service;
        this.userService=userService;
        this.gameLobbyService=gameLobbyService;
    }

    @GetMapping("/invites")
    public ModelAndView showInvitations(){
        ModelAndView result= new ModelAndView(INVITATIONS_VIEW);
        User user= userService.getLoggedInUser().get();
        List<Invitation> invitations= service.getInvitations(user.getUsername());

        result.addObject("invitations",invitations);
        return result;
    }

    @GetMapping("/invites/{lobbyId}/new")
    public ModelAndView createFormInvite(@PathVariable Integer lobbyId){
        ModelAndView result=new ModelAndView(SEND_INVITE_FORM);
        return result;
    }

    @Transactional
    @PostMapping("/invites/{lobbyId}/new")
    public ModelAndView processFormInvite(@PathVariable Integer lobbyId, String username){
        ModelAndView result= new ModelAndView();
        Optional<User> user= userService.findUser(username);
        GameLobby lobby= gameLobbyService.getLobbyById(lobbyId).get();
        Invitation newInvitation=new Invitation();
        if(user.isEmpty()){
            result=new ModelAndView(SEND_INVITE_FORM);
            return result;
        }else{
            newInvitation.setLobby(lobby);
            newInvitation.setUser(user.get());
    
            service.sendInvitation(newInvitation);
            result.setViewName("redirect:/lobby/"+lobbyId);
            result.addObject("message", "Invite succesfully sent!");
        }
        return result;
    }

    @Transactional
    @GetMapping("/invites/{lobbyId}/{inviteId}/accept")
    public ModelAndView acceptInvite(@PathVariable Integer lobbyId,@PathVariable Integer inviteId){
        ModelAndView result=new ModelAndView();
        User user= userService.getLoggedInUser().get();
        Invitation invite=service.findById(inviteId).get();
        if(service.checkAbleToAccept(invite, user.getUsername())){
            service.deleteInvite(inviteId);
            result.setViewName("redirect:/lobby/"+lobbyId);
        }else{
            result.setViewName(INVITATIONS_VIEW);
            List<Invitation> invitations= service.getInvitations(user.getUsername());
            result.addObject("invitations",invitations);
            result.addObject("message","No eres amigo de todos los usuarios del lobby por lo que no puedes unirte.");
        }
        return result;
    }
    
}
