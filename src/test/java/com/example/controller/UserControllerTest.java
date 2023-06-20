package com.example.controller;

import com.example.dto.response.UserResponseDto;
import com.example.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class UserControllerTest {

    private final static String URI = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testGetAll() throws Exception {
        List<UserResponseDto> dtos = List.of(UserResponseDto.builder()
                .id(1L)
                .name("user")
                .username("username123")
                .age(25)
                .address("Yerevan")
                .build());

        given(userService.getAll()).willReturn(dtos);

        mockMvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    public void testGetById() throws Exception {
        UserResponseDto dto = UserResponseDto.builder()
                .id(1L)
                .name("user")
                .username("username123")
                .age(25)
                .address("Yerevan")
                .build();

        given(userService.getById(1L)).willReturn(dto);

        mockMvc.perform(get(URI + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    /*


    @Test
    public void testCreate() {

    }

    @Test
    public void testUpdate() {

    }

    @Test
    public void testDelete() {

    }*/
}
