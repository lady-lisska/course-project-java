package com.lisska.onboarding.controller;

import com.lisska.onboarding.dto.UserCreateDto;
import com.lisska.onboarding.dto.UserDto;
import com.lisska.onboarding.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Пользователи", description = "Управление пользователями системы")
public class UserController {

    private final UserService userService;
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Получить список всех пользователей")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @Operation(summary = "Создать нового пользователя")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto createUser(@RequestBody UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }
}