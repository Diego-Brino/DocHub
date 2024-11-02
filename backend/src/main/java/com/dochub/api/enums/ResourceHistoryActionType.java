package com.dochub.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceHistoryActionType {
    CREATED("CRIADO"),
    EDITED("EDITADO"),
    DELETED("DELETADO");

    private final String code;

    @Override
    public String toString () {
        return this.code;
    }
}