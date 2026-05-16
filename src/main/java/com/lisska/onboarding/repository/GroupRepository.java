package com.lisska.onboarding.repository;

import com.lisska.onboarding.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    // Ищем группы по ID наставника и статусу
    List<Group> findAllByMentorIdAndStatus(Long mentorId, String status);
}