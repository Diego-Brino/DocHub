package com.dochub.api.controllers;

import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.dtos.user.UserResponseDTO;
import com.dochub.api.dtos.user_roles.CreateUserRoleDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.User;
import com.dochub.api.entities.user_role.UserRolePK;
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

@RestController
@RequestMapping("/user-roles")
@RequiredArgsConstructor
public class UserRoleController {
    private final JwtService jwtService;
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    @GetMapping
    public ResponseEntity<UserRoleResponseDTO> getOne (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);

        return ResponseEntity
            .ok()
            .body(userRoleService.getUserRolesByUser(user));
    }

   @PostMapping
   public ResponseEntity<UserRolePK> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                             @RequestBody @Valid @NonNull final CreateUserRoleDTO createUserRoleDTO) {
       final String userEmail = jwtService.extractUserEmail(token);
       final User user = userService.getByEmail(userEmail);

       final UserResponseDTO targetUser = userService.getById(createUserRoleDTO.idUser());
       final RoleResponseDTO targetRole = roleService.getDtoById(createUserRoleDTO.idRole());

       return ResponseEntity
           .status(HttpStatus.CREATED)
           .body(userRoleService.create(user, targetUser.id(), targetRole.id()));
   }

    @DeleteMapping("/{idUser}/{idRole}")
    public ResponseEntity<Void> delete (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("idUser") @NonNull final Integer idUser,
                                        @PathVariable("idRole") @NonNull final Integer idRole) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);

        userRoleService.delete(user, idUser, idRole);

        return ResponseEntity
            .noContent()
            .build();
    }
}