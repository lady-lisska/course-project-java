package com.lisska.onboarding.controller;

import com.lisska.onboarding.dto.AssignmentDto;
import com.lisska.onboarding.model.User;
import com.lisska.onboarding.model.enums.TaskStatus;
import com.lisska.onboarding.repository.UserRepository;
import com.lisska.onboarding.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.lisska.onboarding.dto.AssignmentRequest;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
@Tag(name = "Assignments", description = "Управление назначениями задач и их статусами")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final UserRepository userRepository;

    // --- БЛОК ПРОСМОТРА ---

    @GetMapping
    @Operation(summary = "Получить все назначения в системе (только для Ментора/Админа)")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    public List<AssignmentDto> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/my")
    @Operation(summary = "Получить мои личные задания (доступно всем)")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'USER')")
    public List<AssignmentDto> getMyAssignments(Authentication authentication) {
        // Извлекаем email из JWT токена
        String email = authentication.getName();

        // Находим пользователя, чтобы получить его ID
        User currentUser = userRepository.findByUsername(email)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        // Возвращаем задания только этого пользователя
        return assignmentService.getUserAssignments(currentUser.getId());
    }

    // --- БЛОК УПРАВЛЕНИЯ (СОЗДАНИЕ И СТАТУСЫ) ---

    @PostMapping
    @Operation(summary = "Назначить задачу пользователю (только для Админа)")
    @PreAuthorize("hasRole('ADMIN')")
    public AssignmentDto createAssignment(@RequestBody AssignmentRequest request) {
        return assignmentService.createAssignment(request);
    }

    @PatchMapping("/{id}/take")
    @Operation(summary = "Взять задание в работу (статус IN_PROGRESS)")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'USER')")
    public AssignmentDto takeInWork(@PathVariable Long id) {
        return assignmentService.updateAssignment(id, TaskStatus.IN_PROGRESS, null, null);
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Отправить на проверку (статус DONE_USER)")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'USER')")
    public AssignmentDto completeTask(@PathVariable Long id) {
        return assignmentService.updateAssignment(id, TaskStatus.DONE_USER, null, null);
    }

    @PatchMapping("/{id}/review")
    @Operation(summary = "Оценить и закрыть задачу (только для Ментора/Админа)")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    public AssignmentDto reviewTask(
            @PathVariable Long id,
            @RequestParam Integer score,
            @RequestParam String comment) {
        return assignmentService.updateAssignment(id, TaskStatus.SUCCESS, score, comment);
    }
}