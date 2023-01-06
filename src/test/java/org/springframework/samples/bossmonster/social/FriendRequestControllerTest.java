package org.springframework.samples.bossmonster.social;

import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.configuration.SecurityConfiguration;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import javax.sound.midi.Receiver;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(controllers = FriendRequestController.class,
    excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)
@ExtendWith(MockitoExtension.class)
public class FriendRequestControllerTest {
    
    @MockBean
    private FriendRequestService friendRequestService;

    @MockBean
    private UserService userService;

    @Autowired
    MockMvc mockMvc;

    private FriendRequest testFriendRequest;
    private User igngongon2;
    private User ignarrman;

    @BeforeEach
    void setUp(){
        testFriendRequest = new FriendRequest();
        testFriendRequest.setAccepted(true);
        testFriendRequest.setReceiver(igngongon2);
        testFriendRequest.setRequester(ignarrman);
    }

    @Test
    @WithMockUser
    public void testShowFriendRequestData() throws Exception{
        mockMvc.perform(get("/users/friends/"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testAddFriendForm() throws Exception{
        mockMvc.perform(post("/users/friends/new").with(csrf())
            .param("accepted", "True")
            .param("receiver", "igngongon2")
            .param("requester", "ignarrman"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testNotAddFriendForm() throws Exception{
        mockMvc.perform(post("/users/friends/new").with(csrf())
            .param("accepted", "False")
            .param("receiver", "igngongon2")
            .param("requester", "ignarrman"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testProcessFriendForm() throws Exception{
        mockMvc.perform(post("/users/friends/new").with(csrf())
            .param("accepted", "False")
            .param("receiver", "igngongon2")
            .param("requester", "ignarrman"))
            .andExpect(status().isOk())
            .andExpect(view().name("/friends/createFriendRequest"));
    }

    @Test
    @WithMockUser
    public void testProcessFriendFormHasErrors() throws Exception{
        mockMvc.perform(post("/users/friends/new").with(csrf())
            .param("accepted", "hi")
            .param("receiver", "")
            .param("requester", "ignarrman"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("friendRequest"))
            .andExpect(model().attributeHasFieldErrors("friendRequest", "accepted"))
            .andExpect(model().attributeHasFieldErrors("friendRequest", "receiver"))
            .andExpect(view().name("/friends/createFriendRequest"));
    }
}
