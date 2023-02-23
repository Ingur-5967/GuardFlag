package ru.solomka.guard.core.flag.enums;

import lombok.Getter;

public enum Flag {

    TNT_EXPLOSION("tnt-explosion", new Object[]{"allow", "deny"}),
    BLOCK_BUILD("block-build", new Object[]{"allow", "deny"}),
    SAVE_ITEMS("save-items", new Object[]{"true", "false"}),
    REDSTONE_DISABLE("redstone-disable", new Object[]{"true", "false"}),
    FLY_ENABLE("fly-enable", new Object[]{"true", "false"});

    @Getter private final String idFlag;
    @Getter private final Object[] arguments;

    Flag(String idFlag, Object[] arguments) {
        this.idFlag = idFlag;
        this.arguments = arguments;
    }
}
