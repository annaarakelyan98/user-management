package com.example.service;

import com.example.dto.request.UserCreateRequestDto;
import com.example.dto.request.UserUpdateRequestDto;
import com.example.dto.response.UserResponseDto;
import com.example.entity.UserEntity;
import com.example.repository.UserRepository;
import com.example.service.mapper.UserMapperService;
import com.example.service.user.UserService;
import com.example.service.user.UserServiceImpl;
import com.example.service.validation.UserValidator;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserMapperService userMapperService = new UserMapperService();
    private final UserValidator userValidator = new UserValidator(userRepository);

    private final UserService userService = new UserServiceImpl(
            userRepository, userMapperService, userValidator
    );

    @Test
    void testGetAll() {
        List<UserEntity> userEntities = List.of(UserEntity.builder()
                .id(1L)
                .name("user1")
                .username("username1")
                .age(30)
                .address("yerevan")
                .build());

        when(userRepository.findAll())
                .thenReturn(userEntities);

        List<UserResponseDto> dtos = userService.getAll();

        assertNotNull(dtos);
        assertEquals(1, dtos.size());

        UserResponseDto first = dtos.get(0);
        assertEquals(1L, first.getId());
        assertEquals("user1", first.getName());
        assertEquals("username1", first.getUsername());
        assertEquals(30, first.getAge());
        assertEquals("yerevan", first.getAddress());
    }

    @Test
    void testGetAllWhenEmpty() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<UserResponseDto> dtos = userService.getAll();

        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void testGetById() {
        Long id = 1L;

        UserEntity mockUser = UserEntity.builder()
                .id(id)
                .name("test")
                .build();

        when(userRepository.findById(id))
                .thenReturn(Optional.of(mockUser));

        UserResponseDto dto = userService.getById(id);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("test", dto.getName());
    }

    @Test
    void testGetByIdThrowException() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> userService.getById(id));
    }

    @Test
    void testSave() {
        UserCreateRequestDto dto = UserCreateRequestDto.builder()
                .name("user")
                .username("username1")
                .age(26)
                .address("")
                .build();

        UserResponseDto responseDto = userService.save(dto);

        verify(userRepository).save(Mockito.any());

        assertEquals("user", responseDto.getName());
        assertEquals("username1", responseDto.getUsername());
        assertEquals("", responseDto.getAddress());
        assertEquals(26, responseDto.getAge());
    }

    @Test
    void testUpdate() {
        UserUpdateRequestDto dto = UserUpdateRequestDto.builder()
                .name("user")
                .age(26)
                .address("")
                .build();

        Long id = 1L;

        UserEntity mockUser = UserEntity.builder()
                .id(id)
                .name("user2")
                .username("username1")
                .age(261)
                .address("as")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        UserResponseDto responseDto = userService.update(dto, id);

        verify(userRepository).save(Mockito.any());

        assertEquals("user", responseDto.getName());
        assertEquals("username1", responseDto.getUsername());
        assertEquals("", responseDto.getAddress());
        assertEquals(26, responseDto.getAge());
    }

    @Test
    void testUpdateIdNotFound() {
        UserUpdateRequestDto dto = UserUpdateRequestDto.builder()
                .name("user")
                .age(26)
                .address("")
                .build();

        assertThrows(ObjectNotFoundException.class, () -> userService.update(dto, 1L));

        verify(userRepository, never()).save(Mockito.any());
    }

    @Test
    void testDeleteById() {
        Long id = 1L;

        UserEntity mockUser = UserEntity.builder()
                .id(id)
                .name("test")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        userService.delete(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void testDeleteByIdWhenNotFound() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> userService.delete(id));

        verify(userRepository, never()).deleteById(id);
    }
}
