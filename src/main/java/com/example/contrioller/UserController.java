package com.example.contrioller;

import com.example.dto.requst.UserRequestDto;
import com.example.dto.response.UserResponseDto;
import com.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping
    public UserResponseDto save(@RequestBody UserRequestDto dto) {
        return userService.save(dto);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@RequestBody UserRequestDto dto,
                                  @PathVariable Long id) {
        return userService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
