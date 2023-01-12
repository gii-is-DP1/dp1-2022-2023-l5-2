package org.springframework.samples.bossmonster.invitations;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = InvitationController.class,
    excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class
)

@ExtendWith(MockitoExtension.class)
public class InvitationControllerTests {
    
    @MockBean
    private InvitationService invitationService;

    @Autowired
    private MockMvc mockMvc;

    private Invitation testInvitation;

    @BeforeEach
    void setUp(){
        testInvitation = new Invitation();
        testInvitation.setLobby(null);
        testInvitation.setUser(null);
    }

    @WithMockUser(value = "spring")
    @Test
    public void shouldShowInvitations(){
        mockMvc.perform(get("/invites"))
        .andExpect(status().isOk());
    }
}
