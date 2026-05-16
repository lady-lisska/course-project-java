package com.lisska.onboarding.repository;

import com.lisska.onboarding.model.User;
import com.lisska.onboarding.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // Делаем умный поиск (содержит имя + игнор регистра + исключить роль)
    List<User> findByFullNameContainingIgnoreCaseAndRoleNot(String name, Role role);

    // Найти всех пользователей по ID их группы
    List<User> findAllByGroupId(Long groupId);
}