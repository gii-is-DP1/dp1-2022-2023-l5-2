package org.springframework.samples.bossmonster.user;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTests {
    
    @Autowired
    protected UserService userService;

    @Test
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
    void shouldFindUser() {

        Optional<User> testUser = this.userService.findUser("tadcabgom");
        assertTrue(testUser.isPresent());
        //assertThat(testUser.get().getPassword()).isEqualTo("helloimapassword");
        //assertThat(testUser.get().getNickname()).isEqualTo("Sr. Admin");
        //assertThat(testUser.get().getEmail()).isEqualTo("gnorthway1@wikimedia.org");

    }

    @Test
    void shouldFindLoggedInUser() {

        

    }

}