package ru.solomka.guard.config.enums;

import lombok.Getter;

public enum DirectorySource {


    CACHE("cache"),
    LANG("lang"),
    MENU("menu"),
    DATA("data"),
    NONE("");

    @Getter
    private final String name;

    DirectorySource(String name) {
        this.name = name;
    }

}
