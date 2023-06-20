package com.example.controller;

import com.example.dto.request.UserCreateRequestDto;
import com.example.dto.request.UserUpdateRequestDto;
import com.example.dto.response.UserResponseDto;
import com.example.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class UserControllerTest {

    private final static String URI = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void testGetAll() throws Exception {
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
    void testGetById() throws Exception {
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

    @Test
    void testCreate() throws Exception {
        UserCreateRequestDto dto = UserCreateRequestDto.builder()
                .name("user")
                .username("username")
                .age(23)
                .address("city")
                .build();

        when(userService.save(dto)).thenReturn(UserResponseDto.builder()
                .id(1L)
                .name("user")
                .username("username")
                .age(23)
                .address("city")
                .build());

        mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        UserUpdateRequestDto dto = UserUpdateRequestDto.builder()
                .name("user")
                .age(23)
                .address("city")
                .build();

        given(userService.update(dto, 1L)).willReturn(UserResponseDto.builder()
                .id(1L)
                .name("user")
                .username("username")
                .age(23)
                .address("city")
                .build());

        mockMvc.perform(put(URI + "/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(userService).update(any(), anyLong());
    }

    @Test
    void testDeleteById() throws Exception {
        Mockito.doNothing().when(userService).delete(1L);

        mockMvc.perform(delete(URI + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
