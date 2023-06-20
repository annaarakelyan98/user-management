package com.example.service;

import com.example.dto.request.UserCreateRequestDto;
import com.example.dto.request.UserUpdateRequestDto;
import com.example.dto.response.UserResponseDto;
import com.example.entity.UserEntity;
import com.example.service.mapper.UserMapperService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperServiceTest {

    private final UserMapperService userMapperService = new UserMapperService();

    @Test
    void testMapEntityToDto() {
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .name("user")
                .username("username123")
                .age(25)
                .address("Yerevan")
                .build();

        UserResponseDto dto = userMapperService.mapEntityToDto(userEntity);

        assertEquals(1L, dto.getId());
        assertEquals("user", dto.getName());
        assertEquals("username123", dto.getUsername());
        assertEquals(25, dto.getAge());
        assertEquals("Yerevan", dto.getAddress());
    }

    @Test
    void testCreateDtoToEntity() {
        UserCreateRequestDto userCreateRequestDto = UserCreateRequestDto.builder()
                .name("user1")
                .username("username12.3")
                .age(51)
                .address("Yerevan")
                .build();

        UserEntity userEntity = userMapperService.mapCreateDtoToEntity(userCreateRequestDto);

        assertEquals("user1", userEntity.getName());
        assertEquals("username12.3", userEntity.getUsername());
        assertEquals(51, userEntity.getAge());
        assertEquals("Yerevan", userEntity.getAddress());
    }

    @Test
    void testUpdateDtoToEntity() {
        UserEntity userEntity = UserEntity.builder()
                .name("user2")
                .age(15)
                .address("Abovyan")
                .build();

        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .name("user1")
                .age(51)
                .address("Yerevan")
                .build();

        userMapperService.mapUpdateDtoToEntity(userUpdateRequestDto, userEntity);

        assertEquals("user1", userEntity.getName());
        assertEquals(51, userEntity.getAge());
        assertEquals("Yerevan", userEntity.getAddress());
    }
}
