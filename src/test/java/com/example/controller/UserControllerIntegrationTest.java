package com.example.controller;

import com.example.entity.UserEntity;
import com.example.repository.UserRepository;
import com.example.service.user.UserService;
import com.example.utils.TestFileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    private final static String URI = "/api/v1/users";
    public static final String USER_DTO_PATH = "data/user.json";
    public static final String USERS_DTO_PATH = "data/users.json";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @SqlGroup({@Sql(value = "classpath:db/reset/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/init/user-data.sql", executionPhase = BEFORE_TEST_METHOD)})
    void testGetById() throws Exception {
        mockMvc.perform(get(URI + "/" + 1).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestFileUtils.getContent(USER_DTO_PATH)));
    }

    @Test
    @SqlGroup({@Sql(value = "classpath:db/reset/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/init/user-data.sql", executionPhase = BEFORE_TEST_METHOD)})
    void testGetAll() throws Exception {
        mockMvc.perform(get(URI).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestFileUtils.getContent(USERS_DTO_PATH)));
    }

    @Test
    @SqlGroup({@Sql(value = "classpath:db/reset/reset.sql", executionPhase = BEFORE_TEST_METHOD)})
    void testSaveUsingJson() throws Exception {
        this.mockMvc.perform(post(URI)
                        .contentType(APPLICATION_JSON)
                        .content(TestFileUtils.getContent(USER_DTO_PATH)))
                .andExpect(status().isCreated());

        assertEquals(1, userService.getAll().size());
    }

    @Test
    @SqlGroup({@Sql(value = "classpath:db/reset/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/init/user-data.sql", executionPhase = BEFORE_TEST_METHOD)})
    void testUpdateUsingJson() throws Exception {
        this.mockMvc.perform(put(URI + "/2")
                        .contentType(APPLICATION_JSON)
                        .content(TestFileUtils.getContent(USER_DTO_PATH)))
                .andExpect(status().isOk());

        Optional<UserEntity> optional = userRepository.findById(2L);

        assertTrue(optional.isPresent());
        assertEquals("user1", optional.get().getName());
        assertEquals(25, optional.get().getAge());
        assertEquals("city1", optional.get().getAddress());
    }

    @Test
    @SqlGroup({@Sql(value = "classpath:db/reset/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/init/user-data.sql", executionPhase = BEFORE_TEST_METHOD)})
    void testDeleteById() throws Exception {
        mockMvc.perform(delete(URI + "/" + 1))
                .andExpect(status().isNoContent());

        assertEquals(1, userService.getAll().size());
    }
}
