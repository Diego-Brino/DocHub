package com.dochub.api.dtos.service;

import org.hibernate.validator.constraints.Length;

public record UpdateServiceDTO (
    @Length(max = 256)
    String description
) {
}