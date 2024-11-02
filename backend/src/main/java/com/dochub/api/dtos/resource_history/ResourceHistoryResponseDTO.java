package com.dochub.api.dtos.resource_history;

import com.dochub.api.entities.ResourceHistory;
import com.dochub.api.enums.ResourceHistoryActionType;
import com.dochub.api.utils.Utils;

public record ResourceHistoryResponseDTO (
    Integer id,
    ResourceHistoryActionType actionType,
    String description,
    String actionUser,
    String actionDate
) {
    public ResourceHistoryResponseDTO (final ResourceHistory resourceHistory) {
        this (
            resourceHistory.getId(),
            resourceHistory.getActionType(),
            resourceHistory.getDescription(),
            resourceHistory.getActionUser(),
            Utils.formatDate(resourceHistory.getActionDate())
        );
    }
}
