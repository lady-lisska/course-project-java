package com.lisska.onboarding.service;

import com.lisska.onboarding.dto.UserCreateDto;
import com.lisska.onboarding.dto.UserDto;
import com.lisska.onboarding.model.Group;
import com.lisska.onboarding.model.User;
import com.lisska.onboarding.repository.GroupRepository;
import com.lisska.onboarding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service // Говорим Spring'у, что это класс бизнес-логики
@RequiredArgsConstructor // Lombok сам создаст конструктор для репозиториев
public class UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder; // Внедряем шифратор

    @Transactional
    public UserDto createUser(UserCreateDto dto) {
        // 1. Проверяем бизнес-правило: свободен ли email?
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        // 2. Превращаем DTO в Entity для сохранения в БД
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Шифруем пароль перед сохранением!
        user.setFullName(dto.getFullName());
        user.setRole(dto.getRole());

        // 3. Если прислали ID группы, ищем ее в БД и связываем
        if (dto.getGroupId() != null) {
            Group group = groupRepository.findById(dto.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("Группа с ID " + dto.getGroupId() + " не найдена"));
            user.setGroup(group);
        }

        // 4. Сохраняем в базу
        User savedUser = userRepository.save(user);

        // 5. Возвращаем безопасный DTO
        return mapToDto(savedUser);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto) // Превращаем каждого User в UserDto
                .collect(Collectors.toList());
    }

    // Вспомогательный метод для маппинга (превращения Entity в DTO)
    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .groupName(user.getGroup() != null ? user.getGroup().getName() : null)
                .build();
    }
}