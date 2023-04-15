package ru.solomka.guard.config.lang.entity;

import lombok.Getter;

import java.util.Arrays;

public enum Language {

    RU("ru"),
    EN("en");

    @Getter private final String languageId;

    Language(String languageId) {
        this.languageId = languageId;
    }

    public static boolean isSupported(String id) {
        return Arrays.stream(Language.values()).anyMatch(lang -> lang.getLanguageId().equals(id));
    }
}
