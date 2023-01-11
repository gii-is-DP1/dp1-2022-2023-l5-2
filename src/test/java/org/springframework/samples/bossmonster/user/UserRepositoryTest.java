package org.springframework.samples.bossmonster.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    User user1;
    User user2;
    User user3;

    @BeforeEach
    public void setUp() {
        user1 = new User();
        user1.setUsername("user111");
        user1.setPassword("password");
        user1.setEmail("email@example.com");
        user1.setNickname("null");
        user1.setDescription("blablablabla");
        user2 = new User();
        user2.setUsername("user222");
        user2.setPassword("password");
        user2.setEmail("email@example.com");
        user2.setNickname("null");
        user2.setDescription("blablablabla");
        user3 = new User();
        user3.setUsername("user333");
        user3.setPassword("password");
        user3.setEmail("email@example.com");
        user3.setNickname("null");
        user3.setDescription("blablablabla");

    }

    @Test
    void shouldReturnAllUsers() {
        List<User> expectedUsers = List.of(user1, user2, user3);
        when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> users = userRepository.findAll(); 
        assertEquals(3, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));
    }

    @Test
    void shouldReturnPagedUsers_withPageable() {
        List<User> expectedUsers = List.of(user1, user2);
        Page<User> expectedPage = new PageImpl<>(expectedUsers);
        Pageable pageable = PageRequest.of(0, 2);

        when(userRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<User> usersPage = userRepository.findAll(pageable);

        assertEquals(expectedPage, usersPage);
        verify(userRepository).findAll(pageable);
    }
    
}
