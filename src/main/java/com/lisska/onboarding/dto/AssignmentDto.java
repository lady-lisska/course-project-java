package com.lisska.onboarding.dto;

import com.lisska.onboarding.model.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AssignmentDto {
    private Long id;
    private Long userId;
    private String userFullName; // Для удобства отображения
    private Long taskId;
    private String taskTitle;    // Для удобства отображения
    private TaskStatus status;
    private String mentorComment;
    private Integer score;
    private LocalDateTime deadline;
}