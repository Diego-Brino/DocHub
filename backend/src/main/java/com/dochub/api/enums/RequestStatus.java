package com.dochub.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestStatus {
    IN_PROGRESS("EM ANDAMENTO"),
    FINISHED("FINALIZADO");

    private final String code;

    @Override
    public String toString () {
        return this.code;
    }
}