package ru.solomka.guard.core.flag.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class GFlagComponent<T, C> {
    private final String id;
    private final C paramsFlag;
    private final T controller;
}