package io.vicarius.quotaservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vicarius.quotaservice.domain.document.UserDocument;
import io.vicarius.quotaservice.domain.entity.UserEntity;
import io.vicarius.quotaservice.dto.User;
import io.vicarius.quotaservice.repository.UserElasticSearchRepository;
import io.vicarius.quotaservice.repository.UserJpaRepository;
import io.vicarius.quotaservice.service.UserRepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserJpaRepository userJpaRepository;

    @MockBean
    private UserElasticSearchRepository userElasticSearchRepository;

    @MockBean
    private UserRepositoryService userRepositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUser() throws Exception {
        when(userRepositoryService.getUser("1")).thenReturn(new User("1", "John", "Doe", null));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());

        verify(userRepositoryService).getUser("1");
    }

    @Test
    void testCreateUser() throws Exception {
        var user = new User("1", "John", "Doe", null);
        when(userRepositoryService.createUser(user)).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        verify(userRepositoryService).createUser(user);
    }

    @Test
    void testUpdateUser() throws Exception {
        var user = new User("1", "John", "Doe", null);
        var updatedUser = new User("1", "John", "Doe Jr.", null);
        when(userRepositoryService.getUser("1")).thenReturn(user);
        when(userRepositoryService.createUser(user)).thenReturn(user);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Doe Jr."));

        verify(userRepositoryService).getUser("1");
        verify(userRepositoryService).createUser(updatedUser);
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userRepositoryService).deleteUser("1");
    }
}