package org.springframework.samples.bossmonster.invitations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.social.FriendRequestService;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvitationService {

    InvitationRepository repo;
    FriendRequestService sRequestService;

    @Autowired
    public InvitationService(InvitationRepository repo,FriendRequestService sRequestService){
        this.repo=repo;
        this.sRequestService=sRequestService;
    }
    public Optional<Invitation> findById(Integer id){
        return repo.findById(id);
    }
    public List<Invitation> getInvitations(String username){
        return repo.findAllInvitationsUser(username);
    }
    @Transactional
    public void sendInvitation(Invitation i){
        repo.save(i);
    }
    @Transactional
    public void deleteInvite(Integer id){
        repo.deleteAfterJoin(id);
    }
    public boolean checkAbleToAccept(Invitation i,String username){
        List<User> friends= sRequestService.calculateFriends(username);
        GameLobby lobby= i.getLobby();
        List<User> joinedUsers=lobby.getJoinedUsers();
        if(friends.containsAll(joinedUsers)){
            return true;
        }else{
            return false;
        }
    }

    
}
