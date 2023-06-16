package com.example.service.validation;

import com.example.dto.requst.UserRequestDto;
import com.example.entity.UserEntity;
import com.example.exception.UniqueUsernameException;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateUser(UserRequestDto dto){
       Optional<UserEntity> userOptional = userRepository.findByUsername(dto.getUsername());

       if (userOptional.isPresent()){
           throw new UniqueUsernameException("username must be unique");
       }
    }
}
