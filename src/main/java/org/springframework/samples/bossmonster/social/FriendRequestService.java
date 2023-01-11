package org.springframework.samples.bossmonster.social;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendRequestService {

    FriendRequestRepository repo;
    UserService uService;
    SessionRegistry sessionRegistry;

    @Autowired
    public FriendRequestService(FriendRequestRepository r,UserService uService, SessionRegistry sessionRegistry){
        this.repo=r;
        this.uService=uService;
        this.sessionRegistry=sessionRegistry;
    }

    @Transactional
    public FriendRequest findFriendRequestById(int id){
        return repo.findById(id).get();
    }

    @Transactional
    public List<FriendRequest> findAllReceived(String username){
        return repo.findAllReceived(username);
    }

    @Transactional
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

    @Transactional
    public Boolean checkAlreadyFriends(String senderUsername, String receiverUsername){
        List<User> friends= calculateFriends(senderUsername);
        User receiver= uService.findUser(receiverUsername).get();
        if(friends.contains(receiver)){
            return true;
        }else{
            return false;
        }
    }
    @Transactional
    public Boolean checkAlreadySendOne(String senderUsername, String receiverUsername){
        List<FriendRequest> requests=findAllRequested(senderUsername);
        List<FriendRequest> notAccepted=requests.stream().filter(fr-> fr.getAccepted()==false && fr.getReceiver().getUsername()==receiverUsername).collect(Collectors.toList());
        if(notAccepted.size()>=1){
            return true;
        }else{
            return false;
        }
    }
    @Transactional
    public List<FriendRequest> notAcceptedRequests(String username){
        return repo.findAllNotAccepted(username);
    }
    @Transactional
    public void acceptRequest(String username){
        User me=uService.getLoggedInUser().get();
        repo.acceptFriendRequest(username,me.getUsername());
    }
    @Transactional
    public void declineFriendRequest(String  username){
        User me=uService.getLoggedInUser().get();
        repo.declineFriendRequest(username, me.getUsername());
    }
    @Transactional
    public void saveFriendRequest(FriendRequest fr){
        repo.save(fr);
    }
    @Transactional
    public void unFriendSomeone(String username){
        User me=uService.getLoggedInUser().get();
        repo.unFriend(username, me.getUsername());
    }
    @Transactional
    List<User> loggedInFriends(String username){
        List<Object> loggedIn=sessionRegistry.getAllPrincipals().stream()
        .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
        .map(Object::toString)
        .collect(Collectors.toList());
        List<User> friends= calculateFriends(username);
        List<User> loggedFriends= new ArrayList<User>();
        for(Integer i=0; i<loggedIn.size();i++){
            Object principal= loggedIn.get(i);
            User user= fromPrincipal(principal.toString());

            if(friends.contains(user)){
                loggedFriends.add(user);
            }
        }
        return loggedFriends;
    }
    @Transactional
    public User fromPrincipal(String principal){
        String[] data= principal.split(":");
        String username= data[2].replace("; Password", "").trim();
        User result= uService.findUser(username).get();
        return result;
    }
}
