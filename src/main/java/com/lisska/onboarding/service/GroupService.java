package com.lisska.onboarding.service;

import com.lisska.onboarding.dto.GroupDto;
import com.lisska.onboarding.model.Group;
import com.lisska.onboarding.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public List<GroupDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(group -> GroupDto.builder()
                        .id(group.getId())
                        .name(group.getName())
                        .mentorName(group.getMentor() != null ? group.getMentor().getFullName() : "Не назначен")
                        .build())
                .collect(Collectors.toList());
    }
}