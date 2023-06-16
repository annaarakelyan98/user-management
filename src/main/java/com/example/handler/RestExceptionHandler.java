package com.example.handler;

import com.example.dto.response.ErrorResponseDto;
import com.example.exception.UniqueUsernameException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleObjectNotFoundException(ObjectNotFoundException e){
        var error =  new ErrorResponseDto(e.getMessage());

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(value = {UniqueUsernameException.class})
    public ResponseEntity<ErrorResponseDto> handleUniqueUsernameException(UniqueUsernameException e){
        var error = new ErrorResponseDto(e.getMessage());

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(400));
    }
}
