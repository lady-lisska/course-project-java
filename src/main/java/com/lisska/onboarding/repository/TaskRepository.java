package com.lisska.onboarding.repository;

import com.lisska.onboarding.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Здесь пока оставим пусто, базовых методов CRUD нам хватит
}