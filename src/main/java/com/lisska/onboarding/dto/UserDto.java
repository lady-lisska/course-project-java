package com.lisska.onboarding.dto;

import com.lisska.onboarding.model.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username; // email
    private String fullName;
    private Role role;
    private String groupName; // Отдаем только имя группы, а не весь объект Group
}