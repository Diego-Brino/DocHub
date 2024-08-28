package com.dochub.api.dtos.user;

import com.dochub.api.entity.User;
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
            String.format(Constants.AVATAR_URL, user.getId())
        );
    }
}