package com.dochub.api.controllers;

import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.dtos.system_permission.SystemPermissionResponseDTO;
import com.dochub.api.dtos.system_role_permission.CreateSystemRolePermissionDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.system_role_permission.SystemRolePermissionPK;
import com.dochub.api.entities.User;
import com.dochub.api.services.*;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system-role-permissions")
@RequiredArgsConstructor
public class SystemRolePermissionController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final SystemRolePermissionService systemRolePermissionService;
    private final RoleService roleService;
    private final SystemPermissionService systemPermissionService;

    @PostMapping
    public ResponseEntity<SystemRolePermissionPK> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                          @RequestBody @Valid @NonNull final CreateSystemRolePermissionDTO createSystemRolePermissionDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        final RoleResponseDTO role = roleService.getDtoById(createSystemRolePermissionDTO.idRole());
        final SystemPermissionResponseDTO systemPermission = systemPermissionService.getById(createSystemRolePermissionDTO.idSystemPermission());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(systemRolePermissionService.create(userRoles, role.id(), systemPermission.id()));
    }

    @DeleteMapping("/{idRole}/{idSystemPermission}")
    public ResponseEntity<Void> delete (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("idRole") @NonNull final Integer idRole,
                                        @PathVariable("idSystemPermission") @NonNull final Integer idSystemPermission) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        systemRolePermissionService.delete(userRoles, idRole, idSystemPermission);

        return ResponseEntity
            .noContent()
            .build();
    }
}