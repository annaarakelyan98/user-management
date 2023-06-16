package com.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private String message;
    private List<String> errors = new ArrayList<>();

    public ErrorResponseDto(String message) {
        this.message = message;
    }

}
