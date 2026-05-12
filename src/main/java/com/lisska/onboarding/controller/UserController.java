package com.lisska.onboarding.controller;

import com.lisska.onboarding.dto.UserCreateDto;
import com.lisska.onboarding.dto.UserDto;
import com.lisska.onboarding.repository.UserRepository;
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
    private final UserRepository userRepository;

    // Spring Boot автоматически подставит сюда нужные зависимости
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    @Operation(summary = "Получить список всех пользователей")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    @Operation(summary = "Получить данные текущего авторизованного пользователя")
    public UserDto getCurrentUser(org.springframework.security.core.Authentication authentication) {
        // Ищем пользователя в базе по email (username), который Spring Security достал из JWT
        com.lisska.onboarding.model.User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Превращаем сущность User в красивый UserDto для фронтенда
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .groupName(user.getGroup() != null ? user.getGroup().getName() : "")
                .build();
    }

    @PostMapping
    @Operation(summary = "Создать нового пользователя")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto createUser(@RequestBody UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }
}