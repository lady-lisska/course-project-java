package com.lisska.onboarding.model;

import com.lisska.onboarding.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // Это будет email

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    // Сохраняем Enum в БД как текст (строку), а не как цифру
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Связь: много пользователей могут быть в одной группе
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
}
