package com.dochub.api.controllers;

import com.dochub.api.dtos.group.GroupResponseDTO;
import com.dochub.api.dtos.group_permission.GroupPermissionResponseDTO;
import com.dochub.api.dtos.group_role_permission.CreateGroupRolePermissionDTO;
import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.User;
import com.dochub.api.entities.group_role_permission.GroupRolePermissionPK;
import com.dochub.api.services.*;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-role-permissions")
@RequiredArgsConstructor
public class GroupRolePermissionController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final GroupRolePermissionService groupRolePermissionService;
    private final RoleService roleService;
    private final GroupPermissionService groupPermissionService;
    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupRolePermissionPK> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                         @RequestBody @Valid @NonNull final CreateGroupRolePermissionDTO createGroupRolePermissionDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        final RoleResponseDTO role = roleService.getDtoById(createGroupRolePermissionDTO.idRole());
        final GroupPermissionResponseDTO groupPermission = groupPermissionService.getById(createGroupRolePermissionDTO.idGroupPermission());
        final GroupResponseDTO group = groupService.getById(createGroupRolePermissionDTO.idGroup());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(groupRolePermissionService.create(userRoles, role.id(), groupPermission.id(), group.id()));
    }

    @DeleteMapping("/{idRole}/{idGroupPermission}/{idGroup}")
    public ResponseEntity<Void> delete (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("idRole") @NonNull final Integer idRole,
                                        @PathVariable("idGroupPermission") @NonNull final Integer idGroupPermission,
                                        @PathVariable("idGroup") @NonNull final Integer idGroup) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        groupRolePermissionService.delete(userRoles, idRole, idGroupPermission, idGroup);

        return ResponseEntity
            .noContent()
            .build();
    }
}