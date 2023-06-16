package com.example.service.mapper;

import com.example.dto.request.UserCreateRequestDto;
import com.example.dto.request.UserUpdateRequestDto;
import com.example.dto.response.UserResponseDto;
import com.example.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserMapperService {

    public UserResponseDto mapEntityToDto(UserEntity entity) {
        return UserResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .age(entity.getAge())
                .address(entity.getAddress())
                .build();
    }

    public UserEntity mapCreateDtoToEntity(UserCreateRequestDto dto) {
        return UserEntity.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .address(dto.getAddress())
                .age(dto.getAge())
                .build();
    }

    public void mapUpdateDtoToEntity(UserUpdateRequestDto dto, UserEntity entity) {
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setAddress(dto.getAddress());
    }
}
