package com.dochub.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceType {
    ARCHIVE("Arquivo"),
    FOLDER("Pasta");

    private final String code;

    @Override
    public String toString () {
        return this.code;
    }
}