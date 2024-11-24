package com.dochub.api.controllers;

import com.dochub.api.dtos.activity.ActivityResponseDTO;
import com.dochub.api.dtos.activity.CreateActivityDTO;
import com.dochub.api.dtos.activity.UpdateActivityDTO;
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
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final FlowService flowService;
    private final ActivityService activityService;

    @GetMapping
    public ResponseEntity<List<ActivityResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(activityService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponseDTO> getOne (@PathVariable("id") @NonNull final Integer id) {
        return ResponseEntity
            .ok()
            .body(activityService.getDtoById(id));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateActivityDTO createActivityDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(activityService.create(
                userRoles,
                createActivityDTO
            ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer id,
                                        @RequestBody @Valid @NonNull final UpdateActivityDTO updateActivityDTO) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        activityService.update(
            userRoles,
            id,
            updateActivityDTO,
            flowService::hasFlowsAssignedToActivity
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

        activityService.delete(userRoles, id, flowService::hasFlowsAssignedToActivity);

        return ResponseEntity
            .noContent()
            .build();
    }
}