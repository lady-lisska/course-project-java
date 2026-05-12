package com.lisska.onboarding.service;

import com.lisska.onboarding.dto.AssignmentDto;
import com.lisska.onboarding.dto.AssignmentRequest;
import com.lisska.onboarding.model.Assignment;
import com.lisska.onboarding.model.Task;
import com.lisska.onboarding.model.User;
import com.lisska.onboarding.model.enums.TaskStatus;
import com.lisska.onboarding.repository.AssignmentRepository;
import com.lisska.onboarding.repository.TaskRepository;
import com.lisska.onboarding.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    // Используем явный конструктор
    public AssignmentService(AssignmentRepository assignmentRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    // Метод для создания назначения (доступен Менторам/Админам)
    public AssignmentDto createAssignment(AssignmentRequest dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Шаблон задания не найден"));

        Assignment assignment = new Assignment();
        assignment.setUser(user);
        assignment.setTask(task);
        assignment.setDeadline(dto.getDeadline());
        assignment.setStatus(TaskStatus.CREATED); // Используем статус, который точно есть в Enum

        Assignment savedAssignment = assignmentRepository.save(assignment);
        return mapToDto(savedAssignment); // Вызываем правильное имя метода
    }

    // Метод для получения заданий конкретного пользователя
    public List<AssignmentDto> getUserAssignments(Long userId) {
        return assignmentRepository.findAllByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    // Метод для обновления назначения (смена статуса, оценка)
    public AssignmentDto updateAssignment(Long id, TaskStatus status, Integer score, String comment) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Назначение не найдено"));

        if (status != null) assignment.setStatus(status);
        if (score != null) assignment.setScore(score);
        if (comment != null) assignment.setMentorComment(comment);

        Assignment updated = assignmentRepository.save(assignment);
        return mapToDto(updated);
    }
    // Метод для получения всех назначений в системе (для руководства)
    public List<AssignmentDto> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    // Вспомогательный метод для превращения Сущности в DTO
    private AssignmentDto mapToDto(Assignment assignment) {
        return AssignmentDto.builder()
                .id(assignment.getId())
                .userId(assignment.getUser().getId())
                .userFullName(assignment.getUser().getFullName())
                .taskId(assignment.getTask().getId())
                .taskTitle(assignment.getTask().getTitle())
                .status(assignment.getStatus())
                .mentorComment(assignment.getMentorComment())
                .score(assignment.getScore())
                .deadline(assignment.getDeadline())
                .build();
    }
}