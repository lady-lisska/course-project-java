package com.lisska.onboarding.controller;

import com.lisska.onboarding.dto.TaskDto;
import com.lisska.onboarding.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Задания", description = "Управление шаблонами заданий")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Получить все шаблоны заданий")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'USER')")
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }
}