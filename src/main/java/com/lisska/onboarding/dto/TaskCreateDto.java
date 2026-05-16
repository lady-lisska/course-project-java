package com.lisska.onboarding.dto;

import com.lisska.onboarding.model.ChecklistItem;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskCreateDto {
    private String targetType;
    private Long targetUserId;
    private Long targetGroupId;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private String tags;
    private List<String> links;
    private List<ChecklistItem> checklist;
}