package com.dochub.api.controllers;

import com.dochub.api.dtos.role.CreateRoleDTO;
import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.dtos.role.UpdateRoleDTO;
import com.dochub.api.dtos.user.UserResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Role;
import com.dochub.api.entities.User;
import com.dochub.api.enums.RoleStatus;
import com.dochub.api.services.JwtService;
import com.dochub.api.services.RoleService;
import com.dochub.api.services.UserRoleService;
import com.dochub.api.services.UserService;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final JwtService jwtService;
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(roleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getOne (@PathVariable("id") @NonNull final Integer id) {
        return ResponseEntity
            .ok()
            .body(roleService.getDtoById(id));
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<UserResponseDTO>> getUsersByRole (@PathVariable("id") @NonNull final Integer id) {
        final Role role = roleService.getById(id);

        return ResponseEntity
            .ok()
            .body(userRoleService.getUsersByRole(role));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateRoleDTO createRoleDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(roleService.create(userRoles, createRoleDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer id,
                                        @RequestBody @Valid @NonNull final UpdateRoleDTO updateRoleDTO) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        roleService.update(userRoles, id, updateRoleDTO);

        return ResponseEntity
            .ok()
            .build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                              @PathVariable("id") @NonNull final Integer id,
                                              @RequestParam("roleStatus") @NonNull final RoleStatus roleStatus) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        roleService.updateStatus(userRoles, id, roleStatus);

        return ResponseEntity
            .ok()
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer id) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        roleService.delete(userRoleService::hasUsersAssignedToRole, userRoles, id);

        return ResponseEntity
            .noContent()
            .build();
    }
}