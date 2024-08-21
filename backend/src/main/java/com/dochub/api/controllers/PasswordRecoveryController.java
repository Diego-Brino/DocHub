package com.dochub.api.controllers;

import com.dochub.api.dtos.recover.RecoveryPasswordDTO;
import com.dochub.api.services.PasswordRecoveryService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password-recovery")
@RequiredArgsConstructor
public class PasswordRecoveryController {
    private final PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/link")
    public ResponseEntity<Void> sendRecoveryMail (@RequestParam("email") @NonNull final String userEmail) {
        passwordRecoveryService.sendRecoveryMail(userEmail);

        return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .build();
    }

    @PostMapping("/change")
    public ResponseEntity<Void> changePassword (@RequestBody @Valid final RecoveryPasswordDTO recoveryPasswordDTO) {
        passwordRecoveryService.changePassword(recoveryPasswordDTO);

        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }
}