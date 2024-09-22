package com.dochub.api.controllers;

import com.dochub.api.dtos.group_permission.GroupPermissionResponseDTO;
import com.dochub.api.services.GroupPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group-permissions")
@RequiredArgsConstructor
public class GroupPermissionController {
    private final GroupPermissionService groupPermissionService;

    @GetMapping
    public ResponseEntity<List<GroupPermissionResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(groupPermissionService.getAll());
    }
}