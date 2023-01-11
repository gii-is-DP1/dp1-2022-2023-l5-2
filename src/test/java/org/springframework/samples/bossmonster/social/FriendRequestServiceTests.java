package org.springframework.samples.bossmonster.social;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.invitations.InvitationService;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@Import(SessionRegistry.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class),
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {GameService.class,InvitationService.class}))
    public class FriendRequestServiceTests {
    

    @Autowired
    FriendRequestService friendRequestService;
    
    @MockBean
    SessionRegistry sessionRegistry;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp(){   

    }

    @Test
    void shouldFindAllRecived(){
        var hasRecived = friendRequestService.findAllReceived("igngongon2");
        var noHasRecived = friendRequestService.findAllReceived("user1");
        assertNotNull(hasRecived);
        assertEquals(noHasRecived, new ArrayList<FriendRequest>());
    }

    @Test
    void shouldFindAllRequested(){
        var hasRequested = friendRequestService.findAllRequested("user1");
        var noHasRequested = friendRequestService.findAllRequested("admin1");
        assertNotNull(hasRequested);
        assertEquals(noHasRequested, new ArrayList<FriendRequest>());
    }

    @Test
    void shouldCalculateFriends(){
        FriendRequest fr1 = new FriendRequest();
        fr1.setReceiver(userService.findUser("user1").get());
        fr1.setRequester(userService.findUser("igngongon2").get());
        fr1.setAccepted(true);
        FriendRequest fr2 = new FriendRequest();
        fr2.setReceiver(userService.findUser("fralarmar").get());
        fr2.setRequester(userService.findUser("user1").get());
        fr2.setAccepted(true);
        var first = friendRequestService.calculateFriends("user1").size();
        assertEquals(first, 0);
        friendRequestService.saveFriendRequest(fr1);
        var second = friendRequestService.calculateFriends("user1").size();
        assertEquals(second, 1);
        friendRequestService.saveFriendRequest(fr2);
        var third = friendRequestService.calculateFriends("user1").size();
        assertEquals(third, 2);
    }

    @Test
    void shouldCheckAlreadyFriends(){
        var friends = friendRequestService.checkAlreadyFriends("ignarrman", "igngongon2");
        var noFriends = friendRequestService.checkAlreadyFriends("user1", "igngongon2");
        assertEquals(friends, true);
        assertEquals(noFriends, false);
    }

    @Test
    void shouldCheckAlreadySendOne(){
        var noSend = friendRequestService.checkAlreadySendOne("user1", "igngongon2");
        assertEquals(noSend, false);
        FriendRequest fr1 = new FriendRequest();
        fr1.setReceiver(userService.findUser("igngongon2").get());
        fr1.setRequester(userService.findUser("user1").get());
        fr1.setAccepted(false);
        friendRequestService.saveFriendRequest(fr1);
        var sendNotAccepted = friendRequestService.checkAlreadySendOne("user1", "igngongon2");
        assertEquals(sendNotAccepted, true);
    }

    @Test
    void shouldNotAcceptedResquests(){
        var hasNotAccepted = friendRequestService.notAcceptedRequests("ignarrman").size();
        var hasNoRequests = friendRequestService.notAcceptedRequests("user1").size();
        assertNotEquals(hasNotAccepted, 0);
        assertEquals(hasNoRequests, 0);
    }

    @WithMockUser(value = "ignarrman")
    @Test
    void shouldAcceptRequest(){
        friendRequestService.acceptRequest("user1");
        assertEquals(friendRequestService.checkAlreadyFriends("user1", "ignarrman"), true);;
    }

    @WithMockUser(value = "ignarrman")
    @Test
    void shouldDeclineFriendRequest(){
        friendRequestService.declineFriendRequest("user1");
        assertEquals(friendRequestService.checkAlreadyFriends("user1", "ignarrman"), false);
    }

    @Test
    void shouldSaveFriendRequest(){
        FriendRequest fr = new FriendRequest();
        fr.setReceiver(userService.findUser("ignarrman").get());
        fr.setRequester(userService.findUser("igngongon2").get());
        fr.setAccepted(false);
        friendRequestService.saveFriendRequest(fr);
        assertNotNull(friendRequestService.findFriendRequestById(6));
    }

    @WithMockUser(value = "ignarrman")
    @Test
    void shouldUnFriendSomeone(){
        FriendRequest fr = new FriendRequest();
        fr.setReceiver(userService.findUser("ignarrman").get());
        fr.setRequester(userService.findUser("igngongon2").get());
        fr.setAccepted(true);
        friendRequestService.saveFriendRequest(fr);
        var friend = friendRequestService.checkAlreadyFriends("ignarrman", "igngongon2");
        assertEquals(friend, true);
        friendRequestService.unFriendSomeone("igngongon2");
        var noFriend = friendRequestService.checkAlreadyFriends("ignarrman", "igngongon2");
        assertEquals(noFriend, false);
    }
}
