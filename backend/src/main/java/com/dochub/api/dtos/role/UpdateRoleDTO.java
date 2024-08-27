package com.dochub.api.dtos.role;

import com.dochub.api.enums.RoleStatus;
import org.hibernate.validator.constraints.Length;

public record UpdateRoleDTO (
    @Length(max = 128)
    String name,

    @Length(max = 256)
    String description,

    @Length(max = 32)
    String color,

    RoleStatus roleStatus) {
}