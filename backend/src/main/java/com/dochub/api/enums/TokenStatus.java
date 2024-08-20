package com.dochub.api.enums;

import lombok.Getter;

@Getter
public enum TokenStatus {
    INVALID("Inválido"),
    UNUSED("Não Utilizado"),
    USED("Utilizado");

    private final String code;

    TokenStatus(String code) {
        this.code = code;
    }

    @Override
    public String toString () {
        return this.code;
    }
}
