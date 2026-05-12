package com.lisska.onboarding.controller;

import com.lisska.onboarding.dto.GroupDto;
import com.lisska.onboarding.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Tag(name = "Группы", description = "Управление учебными группами")
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    @Operation(summary = "Получить все группы")
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'USER')")
    public List<GroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }
}