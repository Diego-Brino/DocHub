package com.dochub.api.controllers;

import com.dochub.api.dtos.process.CreateProcessDTO;
import com.dochub.api.dtos.process.ProcessResponseDTO;
import com.dochub.api.dtos.process.UpdateEndDateDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.Service;
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
@RequestMapping("/processes")
@RequiredArgsConstructor
public class ProcessController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RequestService requestService;
    private final ServiceService serviceService;
    private final GroupService groupService;
    private final ProcessService processService;

    @GetMapping
    public ResponseEntity<List<ProcessResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(processService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessResponseDTO> getOne (@PathVariable("id") @NonNull final Integer id) {
        return ResponseEntity
            .ok()
            .body(processService.getDtoById(id));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateProcessDTO createProcessDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Service service = serviceService.getById(createProcessDTO.serviceId());
        final Group group = groupService.getById(createProcessDTO.groupId());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(processService.create(
                userRoles,
                service,
                group
            ));
    }

    @PatchMapping("/{id}/end-date")
    public ResponseEntity<Void> updateEndDate (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                               @PathVariable("id") @NonNull final Integer id,
                                               @RequestBody @Valid @NonNull final UpdateEndDateDTO updateEndDateDTO) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        processService.updateEndDate(
            userRoles,
            id,
            requestService::hasRequestInProgressAssignedToProcess,
            updateEndDateDTO
        );

        return ResponseEntity
            .ok()
            .build();
    }

    @PatchMapping("/{id}/in-progress")
    public ResponseEntity<Void> putProcessInProgress (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                      @PathVariable("id") @NonNull final Integer id) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        processService.putProcessInProgress(userRoles, id);

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

        processService.delete(userRoles, id, requestService::hasRequestAssignedToProcess);

        return ResponseEntity
            .noContent()
            .build();
    }
}