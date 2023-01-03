package org.springframework.samples.bossmonster.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.bossmonster.user.UserService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties="spring.main.lazy-initialization=true")
public class GameBuilderTest {

    @Autowired
    GameBuilder builder;
    @Autowired
    UserService service;

    @Test
    public void shouldBuildGame() {


    }

}
