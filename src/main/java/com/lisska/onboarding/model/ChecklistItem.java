package com.lisska.onboarding.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable // Означает, что это не отдельная таблица, а встраиваемый элемент для Task
@Data
public class ChecklistItem {
    private String text;
    private Boolean isCompleted;
}