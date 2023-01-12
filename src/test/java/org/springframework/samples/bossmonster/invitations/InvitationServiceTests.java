package org.springframework.samples.bossmonster.invitations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.gameLobby.GameLobbyService;
import org.springframework.samples.bossmonster.social.FriendRequestService;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Service;


@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {GameService.class}))
public class InvitationServiceTests {

    @Autowired
    InvitationService service;

    @MockBean
    FriendRequestService friendRequestService;

    @Autowired
    UserService userService;

    @Autowired
    GameLobbyService gameLobbyService;

    @BeforeEach
    void setUp(){

    }

    @Test
    void shouldFindInvitation(){
        Optional<Invitation> invitation= service.findById(0);
        assertTrue(invitation.isPresent());

        invitation= service.findById(100);
        assertTrue(invitation.isEmpty());
    }

    @Test
    void shouldFindAllInvitations(){
        String username="ignarrman";
        List<Invitation> invitations=service.getInvitations(username);
        assertTrue(invitations.size()>0);

    }

    @Test
    void shouldSaveInvitation(){
        Invitation i= new Invitation();
        GameLobby lobby= new GameLobby();
        lobby.setId(2);
        i.setLobby(lobby);
        User testuser= new User();
        testuser.setUsername("ignarrman");
        i.setUser(testuser);
        List<Invitation> previousInvitations=service.getInvitations("ignarrman");
        service.sendInvitation(i);
        List<Invitation> newInvitations=service.getInvitations("ignarrman");
        assertTrue(newInvitations.size()>previousInvitations.size());


    }

    @Test
    void shouldDeleteInvite(){
        Invitation i= new Invitation();
        GameLobby lobby= new GameLobby();
        lobby.setId(2);
        i.setLobby(lobby);
        User testuser= new User();
        testuser.setUsername("ignarrman");
        i.setUser(testuser);
        service.sendInvitation(i);

        service.deleteInvite(i.getId());
        Optional<Invitation> invitation= service.findById(300);
        assertTrue(invitation.isEmpty());
    }

    @Test
    void shouldCheckAbleToAccept(){
        Invitation i= new Invitation();

        User testUser= userService.findUser("admin1").get();
        User testUser2=userService.findUser("eletomvel").get();
        User testUser3= userService.findUser("ignarrman").get();
        GameLobby lobby= gameLobbyService.getLobbyById(3).get();

        i.setLobby(lobby);
        i.setUser(testUser3);
        service.sendInvitation(i);

        List<User> friends=new ArrayList<>();
        friends.add(testUser2);
        friends.add(testUser);

        when(friendRequestService.calculateFriends(anyString())).thenReturn(friends);
        Boolean able= service.checkAbleToAccept(i, "ignarrman");
        assertTrue(able);

        friends.remove(testUser2);
        when(friendRequestService.calculateFriends(anyString())).thenReturn(friends);
        able= service.checkAbleToAccept(i, "ignarrman");
        assertFalse(able);
    }
    
}
