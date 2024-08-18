package com.dochub.api.controllers;

import com.dochub.api.dtos.UpdateUserDTO;
import com.dochub.api.services.UserService;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/avatar")
    public ResponseEntity<byte[]> getUserAvatar (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token) {
        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(userService.getUserAvatar(token));
    }

    @PutMapping
    public ResponseEntity<Void> updateUser (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                            @ModelAttribute @Valid @NonNull final UpdateUserDTO updateUserDTO) {
        userService.updateUser(token, updateUserDTO);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/avatar")
    public ResponseEntity<Void> updateUserAvatar (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                  @RequestPart(Constants.AVATAR) @NonNull final MultipartFile avatar) {
        userService.updateUserAvatar(token, avatar);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updateUserPassword (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                    @RequestBody String password) {
        userService.updateUserPassword(token, password);

        return ResponseEntity.ok().build();
    }
}