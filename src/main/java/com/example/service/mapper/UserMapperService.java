package com.example.service.mapper;

import com.example.dto.requst.UserRequestDto;
import com.example.dto.response.UserResponseDto;
import com.example.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserMapperService {

    public UserResponseDto mapEntityToDto(UserEntity entity){
        return UserResponseDto.builder()
                .name(entity.getName())
                .build();
    }

    public UserEntity mapDtoToEntity(UserRequestDto dto){
        return UserEntity.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .address(dto.getAddress())
                .age(dto.getAge())
                .build();
    }
}
