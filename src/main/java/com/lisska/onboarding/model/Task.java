package com.lisska.onboarding.model;

import jakarta.persistence.*;
import lombok.*;

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

    // TEXT используется для длинных строк, чтобы не упираться в лимит 255 символов
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String checklistJson; // Храним чек-лист в формате JSON

    @Column(columnDefinition = "TEXT")
    private String externalLinks; // Ссылки на файлы
}