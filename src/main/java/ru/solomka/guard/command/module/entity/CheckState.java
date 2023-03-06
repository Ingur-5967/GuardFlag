package ru.solomka.guard.command.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
public class CheckState {

    @Getter private boolean valid;
    @Getter private CallReason reason;

    public enum CallReason {
        INSTANCE_OF,
        PERMISSION,
        OTHER
    }
}