package org.springframework.samples.bossmonster.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.social.FriendRequestService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {GameService.class,FriendRequestService.class}))
public class UserServiceTests {
    
    @Autowired
    protected UserService userService;

    @Test
    @DisplayName("Save an user")
    void shouldSaveUser() {

        User testUser = new User();
        testUser.setUsername("Lumen");
        testUser.setPassword("Celeritas");
        testUser.setEmail("anything@ffs.com");
        testUser.setDescription("The Snow-colored View that Buries the Instant is a nice song");
        testUser.setNickname("Extra");

        int previousSize = userService.findAllUsers().size();
        userService.saveUser(testUser);
        int newSize = userService.findAllUsers().size();

        assertEquals(previousSize+1, newSize);

    }

    @Test
    @DisplayName("Find an user")
    void shouldFindUser() {

        Optional<User> testUser = this.userService.findUser("tadcabgom");
        assertTrue(testUser.isPresent());
        assertThat(testUser.get().getPassword()).isEqualTo("$2a$10$b2gaBr6egp2ohlrvzFXGsu73g/d8jZ03pg4qPVN2dAvQEUAj2ah1e");
        assertThat(testUser.get().getNickname()).isEqualTo("Tadeo");
        assertThat(testUser.get().getEmail()).isEqualTo("iliketrains@gmail.com");
        assertThat(testUser.get().getDescription()).isEqualTo("What I am suppose to write here?");

    }

    @WithMockUser(value = "tadcabgom")
    @Test
    @DisplayName("Find the user that is logged in")
    void shouldFindLoggedInUser() {

        User trueUser = this.userService.findUser("tadcabgom").get();
        User detectedUser = this.userService.getLoggedInUser().get();
        assertEquals(trueUser, detectedUser);

    }

    @Test
    @DisplayName("Find all users")
    void shouldfindAll(){

        List<User> users= userService.findAllUsers();
        assertThat(users.size()>0).isTrue();
    }

    @WithMockUser(value = "admin1")
    @Test
    @DisplayName("Delete an user")
    void shouldDeleteUser(){

        userService.deleteUser("user1");
        assertThat(userService.findUser("user1")).isEqualTo(Optional.empty());
    }

    @WithMockUser(value = "user1")
    @Test
    @DisplayName("Try to delete an user, but shouldn't be able")
    void shouldNotDeleteUser(){

        userService.deleteUser("ignarrman");
        assertThat(userService.findUser("ignarrman")).isNotNull();
    }

}