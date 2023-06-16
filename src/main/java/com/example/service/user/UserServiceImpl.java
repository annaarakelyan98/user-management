package com.example.service.user;

import com.example.dto.request.UserCreateRequestDto;
import com.example.dto.request.UserUpdateRequestDto;
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
    public List<UserResponseDto> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(userMapperService::mapEntityToDto)
                .toList();
    }

    @Override
    public UserResponseDto getById(Long id) {
        UserEntity entity = findUserById(id);

        return userMapperService.mapEntityToDto(entity);
    }

    @Override
    public UserResponseDto save(UserCreateRequestDto dto) {
        userValidator.validateUser(dto);

        UserEntity entity = userMapperService.mapCreateDtoToEntity(dto);

        userRepository.save(entity);

        log.info("User by username : {} created.", dto.getUsername());

        return userMapperService.mapEntityToDto(entity);
    }

    @Override
    public UserResponseDto update(UserUpdateRequestDto dto, Long id) {
        UserEntity entity = findUserById(id);

        userMapperService.mapUpdateDtoToEntity(dto, entity);

        userRepository.save(entity);

        log.info("User by id : '{}' updated.", id);

        return userMapperService.mapEntityToDto(entity);
    }

    @Override
    public void delete(Long id) {
        findUserById(id);

        log.info("User by id : '{}' deleted.", id);

        userRepository.deleteById(id);
    }

    private UserEntity findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "users"));
    }
}
