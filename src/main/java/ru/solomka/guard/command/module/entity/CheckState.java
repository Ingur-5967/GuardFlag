package ru.solomka.guard.command.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class CheckState {

    private boolean valid;
    private CallReason reason;

    public enum CallReason {
        INSTANCE_OF,
        PERMISSION,
        PERMISSION_AND_INSTANCE_OF,
        OTHER
    }
}