package ru.solomka.guard.config.lang.entity;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.config.lang.LangManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public enum Message {

    DENIED_ACCESS_FLAG("%description%"),
    ENTERED_TO_REGION("%region%"),
    LEFT_FROM_REGION("%region%"),
    FLAG_SUCCESS_ADDED("%flag%", "%new_value%"),
    FLAG_SUCCESS_REMOVED("%flag%"),
    FLAG_SUCCESS_CLEAR("%flag%");

    @Getter private final String[] placeholders;

    Message(String ...placeholders) {
        this.placeholders = placeholders;
    }

    public Map<Message, String[]> getMessageSection() {
        Map<Message, String[]> section = new HashMap<>();
        section.put(this, this.getPlaceholders());
        return section;
    }

    public Map<Message, String[]> replaceMessage(@NotNull UnaryOperator<Map<Message, String[]>> func) {
        return func.apply(getMessageSection());
    }
}