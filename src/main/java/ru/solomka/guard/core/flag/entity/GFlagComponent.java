package ru.solomka.guard.core.flag.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class GFlagComponent<C, P> {
    private final String idFlag;
    private final P paramsFlag;
    private final C controller;
}