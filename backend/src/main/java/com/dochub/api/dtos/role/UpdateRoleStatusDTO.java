package com.dochub.api.dtos.role;

import com.dochub.api.enums.RoleStatus;
import lombok.NonNull;

public record UpdateRoleStatusDTO(
    @NonNull
    RoleStatus roleStatus){
}
