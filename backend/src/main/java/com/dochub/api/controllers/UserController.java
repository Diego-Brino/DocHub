package com.dochub.api.controllers;

import com.dochub.api.services.UserService;
import com.dochub.api.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/avatar")
    public ResponseEntity<byte[]> getUserAvatar (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token) {
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(userService.getUserAvatar(token));
    }
}