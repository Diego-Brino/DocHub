package com.dochub.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceOrigin {
    GROUP("GRUPO"),
    FLOW("FLUXO");

    private final String code;

    @Override
    public String toString () {
        return this.code;
    }
}