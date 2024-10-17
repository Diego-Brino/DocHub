package com.dochub.api.controllers;

import com.dochub.api.dtos.user.*;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.User;
import com.dochub.api.services.JwtService;
import com.dochub.api.services.ProfileService;
import com.dochub.api.services.UserRoleService;
import com.dochub.api.services.UserService;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getOne (@PathVariable("id") @NonNull final Integer groupId) {
        return ResponseEntity
            .ok()
            .body(userService.getDtoById(groupId));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @ModelAttribute @NonNull @Valid final ProfileCreateUserDTO profileCreateUserDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(profileService.create(userRoles, profileCreateUserDTO, userService::create));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer targetUserId,
                                        @ModelAttribute @NonNull @Valid final UpdateUserDTO updateUserDTO) {
        final String editorUserEmail = jwtService.extractUserEmail(token);
        final User editorUser = userService.getByEmail(editorUserEmail);
        final UserRoleResponseDTO editorUserRoles = userRoleService.getUserRolesByUser(editorUser);

        final User targetUser = userService.getById(targetUserId);

        profileService.update(editorUserRoles, userService::validateUserUpdate, targetUser, updateUserDTO);

        return ResponseEntity
            .ok()
            .build();
    }

    @PatchMapping("/{id}/avatar")
    public ResponseEntity<Void> updateAvatar (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                              @PathVariable("id") @NonNull final Integer targetUserId,
                                              @RequestPart(Constants.AVATAR_PARAMETER) @NonNull final MultipartFile avatar) {
        final String editorUserEmail = jwtService.extractUserEmail(token);
        final User editorUser = userService.getByEmail(editorUserEmail);
        final UserRoleResponseDTO editorUserRoles = userRoleService.getUserRolesByUser(editorUser);

        final User targetUser = userService.getById(targetUserId);

        profileService.updateAvatar(editorUserRoles, targetUser, avatar);

        return ResponseEntity
            .ok()
            .build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                                 @PathVariable("id") @NonNull final Integer targetUserId,
                                                                 @RequestBody @NonNull @Valid final ProfileUpdateUserPasswordDTO profileUpdateUserPasswordDTO) {
        final String editorUserEmail = jwtService.extractUserEmail(token);
        final User editorUser = userService.getByEmail(editorUserEmail);
        final UserRoleResponseDTO editorUserRoles = userRoleService.getUserRolesByUser(editorUser);

        final User targetUser = userService.getById(targetUserId);

        profileService.updatePassword(editorUserRoles, targetUser, profileUpdateUserPasswordDTO);

        return ResponseEntity
            .ok()
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer userId) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        profileService.delete(userRoles, userId, userService::delete);

        return ResponseEntity
            .noContent()
            .build();
    }
}