package org.springframework.samples.bossmonster.gameLobby;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.exceptions.FullLobbyException;
import org.springframework.samples.bossmonster.exceptions.GameNotFullException;
import org.springframework.samples.bossmonster.exceptions.UserAlreadyPlayingException;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.invitations.InvitationService;
import org.springframework.samples.bossmonster.social.FriendRequestService;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Service;

@ExtendWith({MockitoExtension.class})
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {GameService.class, FriendRequestService.class,InvitationService.class}))
class GameLobbyServiceTest {

    @Autowired
    GameLobbyService lobbyService;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldGetLobbyById() {
        var lobby = lobbyService.getLobbyById(1);
        assertTrue(lobby.isPresent());
    }

    @Test
    void shouldSaveLobby() throws FullLobbyException, UserAlreadyPlayingException, GameNotFullException {
        var lobby = new GameLobby();
        lobby.setMaxPlayers(2);
        User leader= userService.findUser("user1").get();
        lobby.setLeaderUser(leader);
        int previousSize = lobbyService.findAll().size();
        lobbyService.saveLobby(lobby);
        int newSize = lobbyService.findAll().size();

        assertEquals(previousSize+1, newSize);

    }

    @Test
    void shouldDeleteLobby() {

        int previousLobbies = lobbyService.findAll().size();
        GameLobby l = lobbyService.findAll().get(0);
        lobbyService.deleteLobby(l);
        int newLobbies = lobbyService.findAll().size();

        assertEquals(previousLobbies-1, newLobbies);

    }

    @Test
    void shouldVerifyUserPlaying() throws FullLobbyException, UserAlreadyPlayingException, GameNotFullException {
        User dbUser = userService.findAllUsers().get(0);
        User testUser = new User();
        BeanUtils.copyProperties(dbUser,testUser,"username");
        testUser.setUsername("bobby tables");
        userService.saveUser(testUser);

        GameLobby lobby = new GameLobby();
        User leader= userService.findUser("user1").get();
        lobby.setLeaderUser(leader);
        lobby.setMaxPlayers(2);
        lobby.setJoinedUsers(List.of(testUser));
        lobbyService.saveLobby(lobby);

        assertTrue(lobbyService.userIsPlaying(testUser));
    }

    @Test
    void shouldVerifyUserNotPlaying() {
        User dbUser = userService.findAllUsers().get(0);
        User testUser = new User();
        BeanUtils.copyProperties(dbUser,testUser,"username");
        testUser.setUsername("bobby tables");
        userService.saveUser(testUser);

        assertFalse(lobbyService.userIsPlaying(testUser));
    }

    @Test
    void shouldFindGamesByUser() throws FullLobbyException, UserAlreadyPlayingException, GameNotFullException {

        GameLobby l = new GameLobby();
        User testUser = userService.findAllUsers().get(0);

        l.setMaxPlayers(2);
        l.setLeaderUser(testUser);
        l.addUser(testUser);
        int previousGames = lobbyService.findByUser(testUser).size();
        lobbyService.saveLobby(l);
        int newGames = lobbyService.findByUser(testUser).size();

        assertEquals(previousGames+1, newGames);
    }

    @Test
    void shouldntFindGamesByUserWhenDidntPlay() {

        User user = userService.findAllUsers().get(0);
        User testUser = new User();
        BeanUtils.copyProperties(user, testUser, "username");
        testUser.setUsername("bobby tables");
        userService.saveUser(testUser);

        assertTrue(lobbyService.findByUser(testUser).isEmpty());
    }

    @Test
    void shouldFindAllLobbys(){
        List<GameLobby> lobbies= lobbyService.findAll();
        assertNotNull(lobbies);
    }

}
