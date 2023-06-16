package com.example.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequestDto {

    @NotEmpty
    @Size(min = 3, max = 256)
    private String name;

    @NotNull
    @Min(value = 18, message = "Age should be greater than 18")
    @Max(value = 999)
    private int age;

    @Size(min = 3, max = 256)
    private String address;
}
