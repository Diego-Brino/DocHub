package com.dochub.api.controllers;

import com.dochub.api.dtos.resource_role_permission.CreateResourceRolePermissionDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.ResourcePermission;
import com.dochub.api.entities.Role;
import com.dochub.api.entities.User;
import com.dochub.api.entities.resource_role_permission.ResourceRolePermissionPK;
import com.dochub.api.services.*;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource-role-permissions")
@RequiredArgsConstructor
public class ResourceRolePermissionController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ResourceRolePermissionService resourceRolePermissionService;
    private final RoleService roleService;
    private final ResourcePermissionService resourcePermissionService;
    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<ResourceRolePermissionPK> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                            @RequestBody @Valid @NonNull final CreateResourceRolePermissionDTO createResourceRolePermissionDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        final Role role = roleService.getById(createResourceRolePermissionDTO.idRole());
        final ResourcePermission resourcePermission = resourcePermissionService.getById(createResourceRolePermissionDTO.idResourcePermission());
        final Resource resource = resourceService.getById(createResourceRolePermissionDTO.idResource());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(resourceRolePermissionService.create(userRoles, role.getId(), resourcePermission.getId(), resource.getId()));
    }

    @DeleteMapping("/{idRole}/{idResourcePermission}/{idResource}")
    public ResponseEntity<Void> delete (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("idRole") @NonNull final Integer idRole,
                                        @PathVariable("idResourcePermission") @NonNull final Integer idResourcePermission,
                                        @PathVariable("idResource") @NonNull final Integer idResource) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        resourceRolePermissionService.delete(userRoles, idRole, idResourcePermission, idResource);

        return ResponseEntity
            .noContent()
            .build();
    }
}