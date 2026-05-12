package com.lisska.onboarding.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDto {
    private Long id;
    private String name;
    private String mentorName; // Имя ментора, если он назначен
}