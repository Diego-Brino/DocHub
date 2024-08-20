package com.dochub.api.enums;

import lombok.Getter;

@Getter
public enum RoleStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private final String code;

    RoleStatus(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
