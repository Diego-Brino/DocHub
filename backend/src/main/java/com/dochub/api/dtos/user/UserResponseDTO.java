package com.dochub.api.dtos.user;

import com.dochub.api.entities.User;
import com.dochub.api.entities.user_role.UserRole;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;

public record UserResponseDTO (
    Integer id,
    String name,
    String email,
    String username,
    String avatarUrl,
    String lastAccess) {

    public UserResponseDTO (final User user) {
        this(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRealUsername(),
            String.format(Constants.AVATAR_URL + "?v=" + System.currentTimeMillis(), user.getId()),
            Utils.formatDate(user.getLastAccess())
        );
    }

    public UserResponseDTO (final UserRole userRole) {
        this (
            userRole.getUser().getId(),
            userRole.getUser().getName(),
            userRole.getUser().getEmail(),
            userRole.getUser().getRealUsername(),
            String.format(Constants.AVATAR_URL + "?v=" + System.currentTimeMillis(), userRole.getUser().getId()),
            Utils.formatDate(userRole.getUser().getLastAccess())
        );
    }
}