package com.example.service.user;

import com.example.dto.requst.UserRequestDto;
import com.example.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto save(UserRequestDto dto);

    List<UserResponseDto> getAll();

    UserResponseDto getById(Long id);

    UserResponseDto update(UserRequestDto dto, Long id);

    void delete(Long id);
}
