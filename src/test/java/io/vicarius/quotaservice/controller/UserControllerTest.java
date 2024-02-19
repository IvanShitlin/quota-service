package io.vicarius.quotaservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vicarius.quotaservice.QuotaServiceApplication;
import io.vicarius.quotaservice.domain.document.UserDocument;
import io.vicarius.quotaservice.domain.entity.UserEntity;
import io.vicarius.quotaservice.repository.UserElasticSearchRepository;
import io.vicarius.quotaservice.repository.UserJpaRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserJpaRepository userJpaRepository;

    @MockBean
    private UserElasticSearchRepository userElasticSearchRepository;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUser() throws Exception {
        when(userJpaRepository.findById("1")).thenReturn(Optional.of(new UserEntity("1", "John", "Doe", LocalDateTime.now())));
        when(userElasticSearchRepository.findById("1")).thenReturn(Optional.of(UserDocument.builder().id("1").firstName("John").lastName("Doe").lastLoginTimeUtc(1L).build()));
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
        verify(userJpaRepository).findById("1");
    }

    @Test
    void testCreateUser() throws Exception {
        UserEntity user = new UserEntity("2", "John", "Doe", null);
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateUser() throws Exception {
        UserEntity user = new UserEntity("1", "John", "Doe", null);
        mockMvc.perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk());
    }
}