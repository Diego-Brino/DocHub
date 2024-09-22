package com.dochub.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private final String code;

    @Override
    public String toString () {
        return this.code;
    }
}