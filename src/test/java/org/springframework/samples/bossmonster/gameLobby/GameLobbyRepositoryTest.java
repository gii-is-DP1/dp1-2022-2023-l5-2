package org.springframework.samples.bossmonster.gameLobby;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserRepository;
import org.springframework.stereotype.Repository;

import javax.validation.ValidationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class GameLobbyRepositoryTest {

    @Autowired
    GameLobbyRepository lobbyRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldFindAll() {
        assertNotNull(lobbyRepository.findAll());
    }

    @Test
    void shouldSave() {
        var lobby = new GameLobby();
        lobby.setMaxPlayers(2);
        User leader= userRepository.findById("user1").get();
        lobby.setLeaderUser(leader);

        int previousSize = lobbyRepository.findAll().size();
        lobbyRepository.save(lobby);
        int newSize = lobbyRepository.findAll().size();

        assertEquals(previousSize+1, newSize);
    }

    @Test
    void shouldNotSaveWithErrors() {
        var lobby = new GameLobby();
        assertThrows(ValidationException.class, ()->lobbyRepository.save(lobby));
    }

    @Test
    void shouldFindById() {
        GameLobby l = new GameLobby();
        l.setMaxPlayers(2);
        User leader= userRepository.findById("user1").get();
        l.setLeaderUser(leader);
        lobbyRepository.save(l);

        assertTrue(lobbyRepository.findById(l.getId()).isPresent());
    }

    @Test
    void shouldntFindByIdWhenInvalidId() {

        assertFalse(lobbyRepository.findById(-1).isPresent());
    }

    @Test
    void shouldFindByUser() {
        User user = userRepository.findAll().get(0);

        GameLobby lobby = new GameLobby();
        lobby.setMaxPlayers(2);
        lobby.setLeaderUser(user);
        lobby.setJoinedUsers(List.of(user));
        lobbyRepository.save(lobby);

        assertTrue(lobbyRepository.findByUser(user).contains(lobby));
    }

    @Test
    void shouldntFindByUserIfDidntPlay() {
        User user = userRepository.findAll().get(0);
        User testUser = new User();
        BeanUtils.copyProperties(user, testUser, "username");
        testUser.setUsername("pepito");
        userRepository.save(testUser);

        assertTrue(lobbyRepository.findByUser(testUser).isEmpty());
    }
}
