package com.dochub.api.dtos.group;

import com.dochub.api.entities.Group;
import com.dochub.api.utils.Constants;

public record GroupResponseDTO (
    Integer id,
    String name,
    String description,
    String groupUrl
) {
    public GroupResponseDTO (final Group group) {
        this(
            group.getId(),
            group.getName(),
            group.getDescription(),
            String.format(Constants.GROUP_URL + "?v=" + System.currentTimeMillis(), group.getId())
        );
    }
}