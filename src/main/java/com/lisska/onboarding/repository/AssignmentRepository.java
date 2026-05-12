package com.lisska.onboarding.repository;

import com.lisska.onboarding.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    // Найти все задания конкретного пользователя
    List<Assignment> findAllByUserId(Long userId);

    // Найти все назначения конкретного задания
    List<Assignment> findAllByTaskId(Long taskId);
}
