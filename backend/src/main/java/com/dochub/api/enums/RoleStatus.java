package com.dochub.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleStatus {
    ACTIVE("ATIVO"),
    INACTIVE("INATIVO");

    private final String code;

    public static RoleStatus fromCode(String code) {
        for (RoleStatus status : RoleStatus.values()) {
            if (status.getCode().equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + code);
    }

    @Override
    public String toString () {
        return this.code;
    }
}