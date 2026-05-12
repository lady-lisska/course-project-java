package com.lisska.onboarding.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AssignmentRequest {
    private Long userId;
    private Long taskId;
    private LocalDateTime deadline;
}