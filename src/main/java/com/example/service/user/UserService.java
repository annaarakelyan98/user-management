package com.example.service.user;

import com.example.dto.request.UserCreateRequestDto;
import com.example.dto.request.UserUpdateRequestDto;
import com.example.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAll();

    UserResponseDto getById(Long id);

    UserResponseDto save(UserCreateRequestDto dto);

    UserResponseDto update(UserUpdateRequestDto dto, Long id);

    void delete(Long id);
}
