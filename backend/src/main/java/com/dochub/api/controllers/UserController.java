package com.dochub.api.controllers;

import com.dochub.api.dtos.user.UpdatePasswordDTO;
import com.dochub.api.dtos.user.UpdateUserDTO;
import com.dochub.api.dtos.user.UserResponseDTO;
import com.dochub.api.services.UserService;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> get (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token) {
        return ResponseEntity
            .ok()
            .body(userService.getByToken(token));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(userService.getAll());
    }

    @GetMapping(path = "/{id}/avatar", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> getAvatar (@PathVariable("id") @NonNull final Integer id) {
        return ResponseEntity
            .ok()
            .body(userService.getAvatar(id));
    }

    @PutMapping
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @ModelAttribute @NonNull @Valid final UpdateUserDTO updateUserDTO) {
        userService.update(token, updateUserDTO);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/avatar")
    public ResponseEntity<Void> updateAvatar (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                              @RequestPart(Constants.AVATAR_PARAMETER) @NonNull final MultipartFile avatar) {
        userService.updateAvatar(token, avatar);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                @RequestBody @NonNull @Valid final UpdatePasswordDTO updatePasswordDTO) {
        userService.updatePassword(token, updatePasswordDTO);

        return ResponseEntity.ok().build();
    }
}