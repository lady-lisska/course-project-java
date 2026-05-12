package com.lisska.onboarding.model;

import com.lisska.onboarding.model.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Кому назначено задание
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Какое именно задание назначено
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    // Оценка может быть пустой (null), пока ментор не проверит
    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String mentorComment;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isCommentPrivate = false;

    // Hibernate сам подставит время создания записи
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Hibernate сам будет обновлять время при любом изменении записи
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deadline;
}