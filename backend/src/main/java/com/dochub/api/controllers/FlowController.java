package com.dochub.api.controllers;

import com.dochub.api.dtos.flow.CreateFlowDTO;
import com.dochub.api.dtos.flow.FlowResponseDTO;
import com.dochub.api.dtos.flow.UpdateFlowDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Activity;
import com.dochub.api.entities.Process;
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
import java.util.Objects;

@RestController
@RequestMapping("/flows")
@RequiredArgsConstructor
public class FlowController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RequestService requestService;
    private final ProcessService processService;
    private final ActivityService activityService;
    private final FlowService flowService;

    @GetMapping
    public ResponseEntity<List<FlowResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(flowService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlowResponseDTO> getOne (@PathVariable("id") @NonNull final Integer id) {
        return ResponseEntity
            .ok()
            .body(flowService.getDtoById(id));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateFlowDTO createFlowDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Process process = processService.getById(createFlowDTO.processId());
        final Activity activity = activityService.getById(createFlowDTO.activityId());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(flowService.create(
                userRoles,
                requestService::hasRequestAssignedToProcess,
                createFlowDTO,
                process,
                activity
            ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer id,
                                        @RequestBody @Valid @NonNull final UpdateFlowDTO updateFlowDTO) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Activity activity = Objects.nonNull(updateFlowDTO.activityId()) ? activityService.getById(updateFlowDTO.activityId()) : null;

        flowService.update(
            userRoles,
            id,
            requestService::hasRequestAssignedToProcess,
            updateFlowDTO,
            activity
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

        flowService.delete(userRoles, id, requestService::hasRequestAssignedToProcess);

        return ResponseEntity
            .noContent()
            .build();
    }
}