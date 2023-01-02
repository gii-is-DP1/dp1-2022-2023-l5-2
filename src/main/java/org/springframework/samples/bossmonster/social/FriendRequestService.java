package org.springframework.samples.bossmonster.social;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    
    public Boolean checkAlreadyFriends(String senderUsername, String receiverUsername){
        List<User> friends= calculateFriends(senderUsername);
        User receiver= uService.findUser(receiverUsername).get();
        if(friends.contains(receiver)){
            return true;
        }else{
            return false;
        }
    }
    public Boolean checkAlreadySendOne(String senderUsername, String receiverUsername){
        List<FriendRequest> requests=findAllRequested(senderUsername);
        List<FriendRequest> notAccepted=requests.stream().filter(fr-> fr.getAccepted()==false && fr.getReceiver().getUsername()==receiverUsername).collect(Collectors.toList());
        if(notAccepted.size()>=1){
            return true;
        }else{
            return false;
        }
    }
    public List<FriendRequest> notAcceptedRequests(String username){
        return repo.findAllNotAccepted(username);
    }
    public void acceptRequest(String username){
        User me=uService.getLoggedInUser().get();
        repo.acceptFriendRequest(username,me.getUsername());
    }
    public void declineFriendRequest(String  username){
        User me=uService.getLoggedInUser().get();
        repo.declineFriendRequest(username, me.getUsername());
    }
    public void saveFriendRequest(FriendRequest fr){
        repo.save(fr);
    }
    public void unFriendSomeone(String username){
        User me=uService.getLoggedInUser().get();
        repo.unFriend(username, me.getUsername());
    }
}
