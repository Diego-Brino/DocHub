package com.dochub.api.dtos.user;

import com.dochub.api.entities.User;
import com.dochub.api.entities.user_role.UserRole;
import com.dochub.api.utils.Constants;

public record UserResponseDTO (
    Integer id,
    String name,
    String email,
    String username,
    String avatarUrl) {

    public UserResponseDTO (final User user) {
        this(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRealUsername(),
            String.format(Constants.AVATAR_URL + "?v=" + System.currentTimeMillis(), user.getId())
        );
    }

    public UserResponseDTO (final UserRole userRole) {
        this (
            userRole.getUser().getId(),
            userRole.getUser().getName(),
            userRole.getUser().getEmail(),
            userRole.getUser().getRealUsername(),
            String.format(Constants.AVATAR_URL + "?v=" + System.currentTimeMillis(), userRole.getUser().getId())
        );
    }
}