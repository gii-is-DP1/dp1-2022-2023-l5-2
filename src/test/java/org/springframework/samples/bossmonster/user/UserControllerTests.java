package org.springframework.samples.bossmonster.user;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.samples.bossmonster.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = UserController.class,
    excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class
    )
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthoritiesService authoritiesService;

    @Autowired
    private MockMvc mockMvc;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("Saragimaru");
        testUser.setPassword("Tausoken");
        testUser.setEmail("tricknolstalgia@yahoo.es");
        testUser.setDescription("Is a bottle opener!");
        testUser.setNickname("BPoHC");
        testUser.setEnabled(true);

        List<User> users= new ArrayList<>();
        users.add(testUser);
        Page<User> page= new PageImpl<User>(users);

        given(this.userService.findUser(any(String.class))).willReturn(Optional.ofNullable(testUser));
        given(this.userService.getLoggedInUser()).willReturn(Optional.ofNullable(testUser));
        when(userService.findAllUsers()).thenReturn(List.of(testUser));
        when(userService.getPageUsers(any())).thenReturn(page);
    }

    @WithMockUser(value = "spring")
    @Test
    @DisplayName("Creation Form")
    public void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/users/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("user"));
    }


    @WithMockUser(value = "spring")
	@Test
    @DisplayName("Process the Creation Form")
	public void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post("/users/new").with(csrf())
            .param("username", "Adagumo")
            .param("password", "tausoken")
			.param("nickname", "EEEMS")
            .param("email", "pleasework@gmail.com")
            .param("description", "I spent 3 hours doing this please work..."))
			.andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    @DisplayName("Error in creation Form")
    public void testProcessCreationFormHasErrors() throws Exception {
        mockMvc.perform(post("/users/new").with(csrf())
        .param("username", "Adagumo")
        .param("password", "tausoken")
        .param("nickname", "User without description")
        .param("email", "Not an email"))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasErrors("user"))
        .andExpect(model().attributeHasFieldErrors("user", "email"))
        .andExpect(model().attributeHasFieldErrors("user", "description"))
        .andExpect(view().name("users/createUserForm"));
    }

    @WithMockUser(value = "spring")
    @Test
    @DisplayName("Error in creation Form")
    public void testInitUpdateUserForm() throws Exception {
        mockMvc.perform(get("/users/edit"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("user", hasProperty("password", is("Tausoken"))))
        .andExpect(model().attribute("user", hasProperty("nickname", is("BPoHC"))))
        .andExpect(model().attribute("user", hasProperty("email", is("tricknolstalgia@yahoo.es"))))
        .andExpect(model().attribute("user", hasProperty("description", is("Is a bottle opener!"))))
        .andExpect(view().name("users/editUserForm"));
    }

    @WithMockUser(value = "spring")
    @Test
    public void testProcessUpdateUserFormSuccess() throws Exception {
        mockMvc.perform(post("/users/edit").with(csrf())
        .param("username", "Saragimaru")
        .param("password", "password")
        .param("nickname", "Someone")
        .param("email", "idontliketests@alum.us")
        .param("description", "Dont fail again pls"))
        .andExpect(status().isOk())
        .andExpect(view().name("welcome"));
    }

    @WithMockUser(value = "spring")
    @Test
    public void testProcessUpdateUserHasErrors() throws Exception {
        mockMvc.perform(post("/users/edit").with(csrf())
        .param("password", "no")
        .param("nickname", "I am still tired")
        .param("email", "not an email"))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasErrors("user"))
        .andExpect(model().attributeHasFieldErrors("user", "password"))
        .andExpect(model().attributeHasFieldErrors("user", "email"))
        .andExpect(model().attributeHasFieldErrors("user", "description"))
        .andExpect(view().name("users/editUserForm"));
    }

    @WithMockUser(value = "admin1")
    @Test
    public void testuserListingForAdmin() throws Exception{
        mockMvc.perform(get("/admin/users?page=0"))
        .andExpect(status().isOk());
    }

    @WithMockUser(value="admin1")
    @Test
    public void shouldDeleteUser() throws Exception{
        mockMvc.perform(get("/admin/users/user1/delete"))
        .andExpect(view().name("redirect:/admin/users?page=0"))
        .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(value = "admin1")
    @Test
    public void shouldShowEditFormasAdmin() throws Exception{
        mockMvc.perform(get("/admin/users/user1/edit"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("user", hasProperty("password", is("Tausoken"))))
        .andExpect(model().attribute("user", hasProperty("nickname", is("BPoHC"))))
        .andExpect(model().attribute("user", hasProperty("email", is("tricknolstalgia@yahoo.es"))))
        .andExpect(model().attribute("user", hasProperty("description", is("Is a bottle opener!"))))
        .andExpect(view().name("users/editUserAsAdmin"));
    }
    
    @WithMockUser(value = "admin1")
    @Test
    public void shouldProcessEditUserAsAdmin() throws Exception{
        mockMvc.perform(post( "/admin/users/user1/edit").with(csrf())
        .param("username", "Saragimaru")
        .param("password", "newPassword")
        .param("nickname", "editedByAdmin")
        .param("email", "idontliketests@alum.us")
        .param("description", "This time it will succeed, i promise"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/admin/users?page=0"));
    }

    @WithMockUser(value = "admin1")
    @Test
    public void shouldNotProcessEditUserAsAdmin() throws Exception{
        mockMvc.perform(post( "/admin/users/user1/edit").with(csrf())
        .param("username", "Saragimaru")
        .param("password", "short")
        .param("nickname", "There must be something wrong if your nickname is this long")
        .param("email", "emails are for losers"))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasErrors("user"))
        .andExpect(model().attributeHasFieldErrors("user", "password"))
        .andExpect(model().attributeHasFieldErrors("user", "email"))
        .andExpect(model().attributeHasFieldErrors("user", "description"));
    }

}
