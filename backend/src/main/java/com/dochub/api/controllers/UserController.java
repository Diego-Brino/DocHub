package com.dochub.api.controllers;

import com.dochub.api.dtos.flow.FlowResponseDTO;
import com.dochub.api.dtos.request.RequestResponseDTO;
import com.dochub.api.dtos.user.UpdatePasswordDTO;
import com.dochub.api.dtos.user.UpdateUserDTO;
import com.dochub.api.dtos.user.UpdateUserResponseDTO;
import com.dochub.api.dtos.user.UserResponseDTO;
import com.dochub.api.entities.User;
import com.dochub.api.services.FlowService;
import com.dochub.api.services.JwtService;
import com.dochub.api.services.RequestService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;
    private final FlowService flowService;
    private final RequestService requestService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getOne (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                   @PathVariable("id") @NonNull final Integer id) {
        final String userEmail = jwtService.extractUserEmail(token);

        return ResponseEntity
            .ok()
            .body(userService.getDtoById(id, userEmail));
    }

    @GetMapping(path = "/{id}/avatar", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> getAvatar (@PathVariable("id") @NonNull final Integer id) {
        return ResponseEntity
            .ok()
            .body(userService.getAvatar(id));
    }

    @GetMapping("/{id}/flows")
    public ResponseEntity<List<FlowResponseDTO>> getFlows (@PathVariable("id") @NonNull final Integer id) {
        final User user = userService.getById(id);

        return ResponseEntity
            .ok()
            .body(flowService.getAllFlowsInProgressAssignedToUser(user.getId()));
    }

    @GetMapping("/requests")
    public ResponseEntity<List<RequestResponseDTO>> getRequests (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);

        return ResponseEntity
            .ok()
            .body(requestService.getAllRequestAssignedToUser(user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponseDTO> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                         @PathVariable("id") @NonNull final Integer id,
                                                         @ModelAttribute @NonNull @Valid final UpdateUserDTO updateUserDTO) {
        final String userEmail = jwtService.extractUserEmail(token);

        final User user = userService.update(id, userEmail, updateUserDTO);

        return ResponseEntity
            .ok()
            .body(userService.generateNewToken(user, jwtService::generateToken));
    }

    @PatchMapping("/{id}/avatar")
    public ResponseEntity<Void> updateAvatar (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                              @PathVariable("id") @NonNull final Integer id,
                                              @RequestPart(Constants.AVATAR_PARAMETER) @NonNull final MultipartFile avatar) {
        final String userEmail = jwtService.extractUserEmail(token);

        userService.updateAvatar(id, userEmail, avatar);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<UpdateUserResponseDTO> updatePassword (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                                 @PathVariable("id") @NonNull final Integer id,
                                                                 @RequestBody @NonNull @Valid final UpdatePasswordDTO updatePasswordDTO) {
        final String userEmail = jwtService.extractUserEmail(token);

        final User user = userService.updatePassword(id, userEmail, updatePasswordDTO);

        return ResponseEntity
            .ok()
            .body(userService.generateNewToken(user, jwtService::generateToken));
    }
}