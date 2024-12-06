package com.dochub.api.controllers;

import com.dochub.api.dtos.resource.ResourceRolesResponseDTO;
import com.dochub.api.services.ResourceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    @GetMapping("/{id}/permissions")
    public ResponseEntity<ResourceRolesResponseDTO> getResourceRoles (@PathVariable("id") @NonNull final Integer resourceId) {
        return ResponseEntity
            .ok()
            .body(resourceService.getResourceRoles(resourceId));
    }
}