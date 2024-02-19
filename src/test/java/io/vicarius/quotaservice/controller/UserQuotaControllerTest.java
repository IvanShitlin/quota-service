package io.vicarius.quotaservice.controller;

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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserQuotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserJpaRepository userJpaRepository;

    @MockBean
    private UserElasticSearchRepository userElasticSearchRepository;

    @MockBean
    private UserRepositoryService userRepositoryService;

    @Test
    void testConsumeQuotaSuccessfully() throws Exception {
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(post("/users/quota/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void testConsumeQuotaTooManyRequests() throws Exception {
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(post("/users/quota/2")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
        mockMvc.perform(post("/users/quota/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isTooManyRequests());
    }

    @Test
    void testGetUsersQuotaIsChanging() throws Exception {
        var user1 = new User("3", "John", "Doe", LocalDateTime.now());
        when(userRepositoryService.getAllUsers()).thenReturn(List.of(user1));

        mockMvc.perform(get("/users/quota"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value("3"))
                .andExpect(jsonPath("$[0].remainingQuota").value("5"));

        mockMvc.perform(post("/users/quota/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/quota"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value("3"))
                .andExpect(jsonPath("$[0].remainingQuota").value("4"));
    }

}
