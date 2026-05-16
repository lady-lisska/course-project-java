package com.lisska.onboarding.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String tags; // Храним теги строкой: "HR, Безопасность"

    // Spring сам создаст таблицу task_links
    @ElementCollection
    @CollectionTable(name = "task_links", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "link")
    @Builder.Default
    private List<String> links = new ArrayList<>();

    // Spring сам создаст таблицу task_checklists
    @ElementCollection
    @CollectionTable(name = "task_checklists", joinColumns = @JoinColumn(name = "task_id"))
    @Builder.Default
    private List<ChecklistItem> checklist = new ArrayList<>();
}