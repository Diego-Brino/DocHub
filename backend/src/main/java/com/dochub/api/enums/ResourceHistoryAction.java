package com.dochub.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceHistoryAction {
    CREATE("CRIAR"),
    EDIT("EDITAR"),
    DELETE("DELETAR");

    private final String code;

    @Override
    public String toString () {
        return this.code;
    }
}