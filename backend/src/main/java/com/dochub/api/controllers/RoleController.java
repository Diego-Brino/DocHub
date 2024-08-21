package com.dochub.api.controllers;

import com.dochub.api.dtos.role.CreateRoleDTO;
import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.services.RoleService;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> get (@PathVariable("id") @NonNull final Integer id) {
        return ResponseEntity
            .ok()
            .body(roleService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(roleService.getAll());
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateRoleDTO createRoleDTO) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(roleService.create(token, createRoleDTO));
    }
}