package com.lisska.onboarding.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "groups") // 'group' - зарезервированное слово в SQL
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "mentor_id")
    private Long mentorId; // ID наставника, который ведет этот поток

    @Column(name = "created_at")
    private LocalDateTime createdAt; // Дата создания потока

    @Column(name = "status")
    private String status; // Статус группы: ACTIVE или COMPLETED

    // Перед первым сохранением в базу автоматически проставляем дату и статус
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "ACTIVE"; // По умолчанию группа активна
        }
    }
}