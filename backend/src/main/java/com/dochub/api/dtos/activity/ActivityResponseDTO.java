package com.dochub.api.dtos.activity;

import com.dochub.api.entities.Activity;

public record ActivityResponseDTO (
    Integer id,
    String description
) {
    public ActivityResponseDTO (final Activity activity) {
        this (
            activity.getId(),
            activity.getDescription()
        );
    }
}