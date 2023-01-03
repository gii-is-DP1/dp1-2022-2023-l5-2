package org.springframework.samples.bossmonster.social;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.FilterType;

import java.util.*;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class),
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = GameService.class))
    public class FriendRequestServiceTests {

    @Autowired
    FriendRequestService friendRequestService;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp(){
    /*     FriendRequest friendRequest1 = new FriendRequest();
        org.springframework.samples.bossmonster.user.User igngongon2 = new org.springframework.samples.bossmonster.user.User();
        org.springframework.samples.bossmonster.user.User user1 = new org.springframework.samples.bossmonster.user.User();
        friendRequest1.setAccepted(true);
        friendRequest1.setReceiver(igngongon2);
        friendRequest1.setRequester(user1);

        FriendRequest friendRequest2 = new FriendRequest();
        org.springframework.samples.bossmonster.user.User user2 = new org.springframework.samples.bossmonster.user.User();
        friendRequest2.setAccepted(false);
        friendRequest2.setReceiver(igngongon2);
        friendRequest2.setRequester(user2); */
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

    @Test //No se si está completo
    void shouldCalculateFriends(){
        var a = friendRequestService.calculateFriends("admin1");
        assertEquals(a, 0);
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
        fr.setId(100);
        fr.setReceiver(userService.findUser("ignarrman").get());
        fr.setRequester(userService.findUser("igngongon2").get());
        fr.setAccepted(false);
        friendRequestService.saveFriendRequest(fr);
        assertNotNull(friendRequestService.findFriendRequestById(100));
    }

    @Test
    void shouldUnFriendSomeone(){

    }
}
