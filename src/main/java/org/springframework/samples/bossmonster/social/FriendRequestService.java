package org.springframework.samples.bossmonster.social;

import java.util.List;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestService {

    private FriendRequestRepository repo;

    @Autowired
    public FriendRequestService(FriendRequestRepository r){
        this.repo=r;
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

    public List<User> calculateFriends(String username){
        List<User> received= repo.findAllReceivedAccepted(username);
        List<User> requests= repo.findAllRequestedAccepted(username);
        received.addAll(requests);
        List<User> total= received;
        return total;
    }
}
