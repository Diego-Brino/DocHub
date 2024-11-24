package com.dochub.api.controllers;

import com.dochub.api.dtos.movement.CreateMovementDTO;
import com.dochub.api.dtos.movement.MovementResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Flow;
import com.dochub.api.entities.Request;
import com.dochub.api.entities.Response;
import com.dochub.api.entities.User;
import com.dochub.api.services.*;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movements")
@RequiredArgsConstructor
public class MovementController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ProcessService processService;
    private final RequestService requestService;
    private final FlowService flowService;
    private final ResponseService responseService;
    private final MovementService movementService;

    @GetMapping
    public ResponseEntity<List<MovementResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(movementService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementResponseDTO> getOne (@PathVariable("id") @NonNull final Integer id) {
        return ResponseEntity
            .ok()
            .body(movementService.getDtoById(id));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateMovementDTO createMovementDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Request request = requestService.getById(createMovementDTO.requestId());
        final Flow flow = flowService.getById(createMovementDTO.flowId());
        final Response response = responseService.getById(createMovementDTO.responseId());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(movementService.create(
                userRoles,
                processService::isProcessFinished,
                requestService::setRequestAsFinished,
                request,
                flow,
                response
            ));
    }
}