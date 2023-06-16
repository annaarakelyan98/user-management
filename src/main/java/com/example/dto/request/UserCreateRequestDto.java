package com.example.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateRequestDto {

    @NotEmpty
    @Size(min = 3, max = 256)
    private String name;

    @NotEmpty
    @Size(min = 3, max = 256)
    private String username;

    @NotNull
    @Min(value = 18, message = "Age should be greater than 18")
    @Max(value = 999)
    private Integer age;

    @Size(min = 3, max = 256)
    private String address;
}
