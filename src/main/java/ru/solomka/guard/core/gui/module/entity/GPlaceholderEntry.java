package ru.solomka.guard.core.gui.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class GPlaceholderEntry {
    private final String replacement;
    private final Object value;
}
