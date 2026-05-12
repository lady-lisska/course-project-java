package com.lisska.onboarding.dto;

import com.lisska.onboarding.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateDto {

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String username;

    @NotBlank(message = "Пароль обязателен")
    private String password;

    @NotBlank(message = "ФИО обязательно")
    private String fullName;

    @NotNull(message = "Роль обязательна")
    private Role role;

    private Long groupId; // ID группы, куда хотим назначить пользователя
}