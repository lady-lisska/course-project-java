package com.lisska.onboarding.service;

import com.lisska.onboarding.dto.GroupDto;
import com.lisska.onboarding.model.User;
import com.lisska.onboarding.repository.GroupRepository;
import com.lisska.onboarding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    // Добавляем UserRepository, чтобы искать ментора по его ID
    private final UserRepository userRepository;

    public List<GroupDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(group -> {
                    // Вытаскиваем имя ментора по его mentorId
                    String mentorName = "Не назначен";
                    if (group.getMentorId() != null) {
                        mentorName = userRepository.findById(group.getMentorId())
                                .map(User::getFullName)
                                .orElse("Наставник удален");
                    }

                    return GroupDto.builder()
                            .id(group.getId())
                            .name(group.getName())
                            .mentorName(mentorName)
                            .build();
                })
                .collect(Collectors.toList());
    }
}