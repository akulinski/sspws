package com.akulinski.sspws.core.components.controllers.api;

import com.akulinski.sspws.core.components.repositories.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(username = "tomeczek", password = "tomeczek")
    public void getAll() throws Exception {
        this.mockMvc.perform(get("/users/getAllUsers")).andDo(print()).andExpect(status().isAccepted())
                .andExpect(content().string(containsString("tomeczek")));
    }

    @Test
    @WithMockUser(username = "tomeczek", password = "tomeczek")
    public void getUserById() throws Exception {

        Integer id = userRepository.getByUsername("tomeczek").getId();
        String url = String.format("/users/getUserById/%s", id);

        this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isAccepted())
                .andExpect(content().string(containsString("tomeczek")));
    }

}