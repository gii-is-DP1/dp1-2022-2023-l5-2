package org.springframework.samples.bossmonster.social;

import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendRequestService {

    private FriendRequestRepository repo;
    private UserService uService;

    @Autowired
    public FriendRequestService(FriendRequestRepository r,UserService uService){
        this.repo=r;
        this.uService=uService;
    }
    
    public List<FriendRequest> findAllReceived(String username){
        return repo.findAllReceived(username);
    }

    public List<FriendRequest> findAllRequested(String username){
        return repo.findAllRequested(username);
    }

    public void acceptFriendRequest(int id){
        repo.acceptFriendRequest(id);
    }

    public void declineFriendRequest(int id){
        repo.declineFriendRequest(id);
    }

    @Transactional(readOnly = true)
    public List<User> calculateFriends(String username){
        List<String> received= repo.findAllReceivedAccepted(username);
        List<String> requests= repo.findAllRequestedAccepted(username);
        List<User> friends= new ArrayList<>();
        for(Integer i=0; i<received.size();i++){
            User user= uService.findUser(received.get(i)).get();
            friends.add(user);
        }
        for(Integer j=0; j<requests.size(); j++){
            User user= uService.findUser(requests.get(j)).get();
            friends.add(user);
        }
        return friends;
    }
}
