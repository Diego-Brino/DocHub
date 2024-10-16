package com.dochub.api.controllers;

import com.dochub.api.dtos.resource_permission.ResourcePermissionResponseDTO;
import com.dochub.api.services.ResourcePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resource-permissions")
@RequiredArgsConstructor
public class ResourcePermissionController {
    private final ResourcePermissionService resourcePermissionService;

    @GetMapping
    public ResponseEntity<List<ResourcePermissionResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(resourcePermissionService.getAll());
    }
}