package com.example.service.user;

import com.example.dto.requst.UserRequestDto;
import com.example.dto.response.UserResponseDto;
import com.example.entity.UserEntity;
import com.example.repository.UserRepository;
import com.example.service.mapper.UserMapperService;
import com.example.service.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapperService userMapperService;
    private final UserValidator userValidator;

    @Override
    public UserResponseDto save(UserRequestDto dto) {
        userValidator.validateUser(dto);

        UserEntity entity = userMapperService.mapDtoToEntity(dto);

        userRepository.save(entity);

        log.info("User by username : {} created!", dto.getUsername());

        return userMapperService.mapEntityToDto(entity);
    }

    @Override
    public List<UserResponseDto> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(userMapperService::mapEntityToDto)
                .toList();
    }

    @Override
    public UserResponseDto getById(Long id) {
        return userRepository.findById(id)
                .map(userMapperService::mapEntityToDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, "users"));
    }

    @Override
    public UserResponseDto update(UserRequestDto dto, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "users"));

        userRepository.deleteById(id);
    }
}
