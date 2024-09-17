package com.dochub.api.controllers;

import com.dochub.api.dtos.system_permission.SystemPermissionResponseDTO;
import com.dochub.api.services.SystemPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system-permissions")
@RequiredArgsConstructor
public class SystemPermissionController {
    private final SystemPermissionService systemPermissionService;

    @GetMapping
    public ResponseEntity<List<SystemPermissionResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(systemPermissionService.getAll());
    }
}