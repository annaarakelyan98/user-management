package com.example.service.validation;

import com.example.dto.request.UserCreateRequestDto;
import com.example.entity.UserEntity;
import com.example.exception.UniqueUsernameException;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateUser(UserCreateRequestDto dto){
       Optional<UserEntity> userOptional = userRepository.findByUsername(dto.getUsername());

       if (userOptional.isPresent()){
           log.info("User already exists by username : '{}'", dto.getUsername());

           throw new UniqueUsernameException("username must be unique");
       }
    }
}
