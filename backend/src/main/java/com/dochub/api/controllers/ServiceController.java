package com.dochub.api.controllers;

import com.dochub.api.dtos.service.CreateServiceDTO;
import com.dochub.api.dtos.service.ServiceResponseDTO;
import com.dochub.api.dtos.service.UpdateServiceDTO;
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
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ProcessService processService;
    private final ServiceService serviceService;

    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(serviceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> getOne (@PathVariable("id") @NonNull final Integer serviceId) {
        return ResponseEntity
            .ok()
            .body(serviceService.getDtoById(serviceId));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateServiceDTO createServiceDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(serviceService.create(userRoles, createServiceDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer id,
                                        @RequestBody @Valid @NonNull final UpdateServiceDTO updateRoleDTO) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        serviceService.update(userRoles, id, updateRoleDTO);

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

        serviceService.delete(userRoles, id, processService::hasProcessAssignedToService);

        return ResponseEntity
            .noContent()
            .build();
    }
}