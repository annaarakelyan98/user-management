package com.example.controller;

import com.example.dto.response.UserResponseDto;
import com.example.repository.UserRepository;
import com.example.service.user.UserService;
import com.example.utils.TestFileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    private final static String URI = "/api/v1/users";
    public static final String USER_DTO_PATH = "data/user.json";

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
        UserResponseDto dto = UserResponseDto.builder()
                .id(1L)
                .name("user1")
                .username("username1")
                .age(25)
                .address("city1")
                .build();

        mockMvc.perform(get(URI + "/" + 1).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    @SqlGroup({@Sql(value = "classpath:db/reset/reset.sql", executionPhase = BEFORE_TEST_METHOD)})
    void testSaveUsingJson() throws Exception {
        this.mockMvc.perform(post(URI)
                        .contentType(APPLICATION_JSON)
                        .content(TestFileUtils.getContent(USER_DTO_PATH)))
                .andExpect(status().isCreated());

        Assertions.assertEquals(1, userService.getAll().size());
    }

}
