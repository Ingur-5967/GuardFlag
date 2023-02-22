package ru.solomka.guard.config.enums;

import lombok.Getter;

public enum DirectorySource {

    PLAYER("playerdata"),
    MENU("menu"),
    DATA("data"),
    NONE("");

    @Getter
    private final String type;

    DirectorySource(String type) {
        this.type = type;
    }

}
