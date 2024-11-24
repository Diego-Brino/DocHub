package com.dochub.api.controllers;

import com.dochub.api.dtos.response.CreateResponseDTO;
import com.dochub.api.dtos.response.ResponseResponseDTO;
import com.dochub.api.dtos.response.UpdateResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.User;
import com.dochub.api.services.*;
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
@RequestMapping("/responses")
@RequiredArgsConstructor
public class ResponseController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ResponseFlowService responseFlowService;
    private final ResponseService responseService;

    @GetMapping
    public ResponseEntity<List<ResponseResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(responseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseResponseDTO> getOne (@PathVariable("id") @NonNull final Integer id) {
        return ResponseEntity
            .ok()
            .body(responseService.getDtoById(id));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateResponseDTO createResponseDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responseService.create(
                userRoles,
                createResponseDTO
            ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer id,
                                        @RequestBody @Valid @NonNull final UpdateResponseDTO updateResponseDTO) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        responseService.update(
            userRoles,
            id,
            updateResponseDTO,
            responseFlowService::hasResponseFlowsAssignedToResponse
        );

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

        responseService.delete(userRoles, id, responseFlowService::hasResponseFlowsAssignedToResponse);

        return ResponseEntity
            .noContent()
            .build();
    }
}