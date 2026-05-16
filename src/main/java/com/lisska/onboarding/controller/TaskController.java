package com.lisska.onboarding.controller;

import com.lisska.onboarding.dto.TaskCreateDto;
import com.lisska.onboarding.dto.TaskDto;
import com.lisska.onboarding.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Задания", description = "Управление шаблонами заданий")
public class TaskController {

    private final TaskService taskService;

    // --- МЕТОД GET ---
    @GetMapping
    @Operation(summary = "Получить все шаблоны заданий")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'USER')")
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    // --- МЕТОД POST ---
    @PostMapping
    @Operation(summary = "Создать задачу и назначить её (себе, сотруднику или группе)")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'USER')")
    public ResponseEntity<?> createTask(@RequestBody TaskCreateDto dto, Authentication authentication) {
        taskService.createTaskAndAssign(dto, authentication.getName());
        return ResponseEntity.ok().body("{\"message\": \"Задача успешно создана и назначена!\"}");
    }
}