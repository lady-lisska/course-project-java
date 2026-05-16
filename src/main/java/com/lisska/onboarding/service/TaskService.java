package com.lisska.onboarding.service;

import com.lisska.onboarding.dto.TaskCreateDto;
import com.lisska.onboarding.dto.TaskDto;
import com.lisska.onboarding.model.Assignment;
import com.lisska.onboarding.model.Task;
import com.lisska.onboarding.model.User;
import com.lisska.onboarding.model.enums.TaskStatus;
import com.lisska.onboarding.repository.AssignmentRepository;
import com.lisska.onboarding.repository.TaskRepository;
import com.lisska.onboarding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    // Получение всех шаблонов
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(task -> TaskDto.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    // Создание шаблона и назначение
    @Transactional
    public void createTaskAndAssign(TaskCreateDto dto, String currentUsername) {
        // 1. Ищем того, кто нажал кнопку "Создать"
        User creator = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // 2. Создаем ШАБЛОН задачи (Один на всех)
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setTags(dto.getTags());
        task.setLinks(dto.getLinks());
        task.setChecklist(dto.getChecklist());
        // Сохраняем шаблон в БД, чтобы получить его ID
        task = taskRepository.save(task);

        // 3. Раздаем назначения (Assignments) в зависимости от выбора в меню
        if ("PERSONAL".equals(dto.getTargetType())) {
            createAssignment(task, creator, dto);
        } else if ("USER".equals(dto.getTargetType())) {
            User targetUser = userRepository.findById(dto.getTargetUserId())
                    .orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
            createAssignment(task, targetUser, dto);
        } else if ("GROUP".equals(dto.getTargetType())) {
            List<User> groupUsers = userRepository.findAllByGroupId(dto.getTargetGroupId());
            for (User student : groupUsers) {
                createAssignment(task, student, dto);
            }
        }
    }

    // Вспомогательный метод для штамповки назначений
    private void createAssignment(Task task, User user, TaskCreateDto dto) {
        Assignment assignment = new Assignment();
        assignment.setTask(task);
        assignment.setUser(user);
        assignment.setStatus(TaskStatus.CREATED);
        assignment.setDeadline(dto.getDeadline());
        assignmentRepository.save(assignment);
    }
}