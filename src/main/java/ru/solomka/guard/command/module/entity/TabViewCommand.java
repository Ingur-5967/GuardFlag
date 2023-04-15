package ru.solomka.guard.command.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class TabViewCommand<T> {
    private final int index;
    private final T[] toView;
}