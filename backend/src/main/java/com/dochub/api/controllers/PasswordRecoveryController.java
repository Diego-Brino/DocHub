package com.dochub.api.controllers;

import com.dochub.api.dtos.recovery_password.RecoveryPasswordDTO;
import com.dochub.api.entity.User;
import com.dochub.api.services.PasswordRecoveryService;
import com.dochub.api.services.UserService;
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
    private final UserService userService;
    private final PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/link")
    public ResponseEntity<Void> sendRecoveryMail (@RequestParam("email") @NonNull final String userEmail) {
        final User user = userService.getByEmail(userEmail);

        passwordRecoveryService.sendRecoveryMail(user);

        return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .build();
    }

    @PostMapping("/change")
    public ResponseEntity<Void> changePassword (@RequestBody @Valid final RecoveryPasswordDTO recoveryPasswordDTO) {
        passwordRecoveryService.changePassword(recoveryPasswordDTO, userService::updatePasswordByRecoveryLink);

        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }
}