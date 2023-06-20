package com.example.service;

import com.example.dto.request.UserCreateRequestDto;
import com.example.entity.UserEntity;
import com.example.exception.UniqueUsernameException;
import com.example.repository.UserRepository;
import com.example.service.validation.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserValidatorServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserValidator userValidator = new UserValidator(userRepository);

    @Test
    void testValidateUser() {
        UserCreateRequestDto userCreateRequestDto = UserCreateRequestDto.builder()
                .name("user")
                .username("username1")
                .age(30)
                .address("Abovyan")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .name("user")
                .username("username1")
                .age(30)
                .address("Abovyan")
                .build();

        when(userRepository.findByUsername(userCreateRequestDto.getUsername())).thenReturn(Optional.of(userEntity));

        Assertions.assertThrows(UniqueUsernameException.class,
                () -> userValidator.validateUser(userCreateRequestDto));
    }
}
