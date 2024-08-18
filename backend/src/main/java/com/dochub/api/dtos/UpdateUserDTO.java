package com.dochub.api.dtos;

import org.springframework.web.multipart.MultipartFile;

public record UpdateUserDTO (
    String name,
    String password,
    String email,
    String username,
    MultipartFile avatar) {
}