package com.dochub.api.controllers;

import com.dochub.api.dtos.ChangeUserPasswordByResetLinkDTO;
import com.dochub.api.services.ResetPasswordService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset-password")
@RequiredArgsConstructor
public class ResetPasswordController {
    private final ResetPasswordService resetPasswordService;

    @PostMapping("/link")
    public ResponseEntity<Void> resetLink (@RequestParam("email") @NonNull final String userEmail) {
        resetPasswordService.sendMailToResetPassword(userEmail);

        return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .build();
    }

    @PostMapping("/change")
    public ResponseEntity<Void> changePassword (@RequestBody @Valid final ChangeUserPasswordByResetLinkDTO changeUserPasswordByResetLinkDTO) {
        resetPasswordService.changePassword(changeUserPasswordByResetLinkDTO);

        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }
}