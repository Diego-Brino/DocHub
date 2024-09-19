package com.dochub.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleStatus {
    ACTIVE("ATIVO"),
    INACTIVE("INATIVO");

    private final String code;

    @Override
    public String toString () {
        return this.code;
    }
}