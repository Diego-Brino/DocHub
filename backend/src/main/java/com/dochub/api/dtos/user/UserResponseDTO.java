package com.dochub.api.dtos.user;

import com.dochub.api.entity.User;
import com.dochub.api.utils.Constants;

public record UserResponseDTO (
    String name,
    String email,
    String username,
    String avatarUrl) {

    public UserResponseDTO (final User user) {
        this(
            user.getName(),
            user.getEmail(),
            user.getUsername(),
            String.format(Constants.AVATAR_URL, user.getId())
        );
    }
}