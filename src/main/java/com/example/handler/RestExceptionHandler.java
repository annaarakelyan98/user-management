package com.example.handler;

import com.example.dto.response.ErrorResponseDto;
import com.example.exception.UniqueUsernameException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleObjectNotFoundException(ObjectNotFoundException e) {
        var error = new ErrorResponseDto(e.getMessage());

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(value = {UniqueUsernameException.class})
    public ResponseEntity<ErrorResponseDto> handleUniqueUsernameException(UniqueUsernameException e) {
        var error = new ErrorResponseDto(e.getMessage());

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var error = new ErrorResponseDto();

        e.getFieldErrors().forEach(it -> error.getErrors().add(it.getField() + " : " + it.getDefaultMessage()));

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponseDto> handleConstraintViolationException(ConstraintViolationException e) {
        var error = new ErrorResponseDto(e.getMessage());

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
}
