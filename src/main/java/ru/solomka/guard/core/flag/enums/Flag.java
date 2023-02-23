package ru.solomka.guard.core.flag.enums;

import lombok.Getter;
import org.bukkit.event.Event;
import ru.solomka.guard.core.flag.event.RegionEnteringEvent;
import ru.solomka.guard.core.flag.impl.FlyFlag;

public enum Flag {

    TNT_EXPLOSION("tnt-explosion", new Object[]{"allow", "deny"}, ContextFlag.BREAK),
    BLOCK_BUILD("block-build", new Object[]{"allow", "deny"}, ContextFlag.BREAK, ContextFlag.PLACE),
    SAVE_ITEMS("save-items", new Object[]{"true", "false"}, ContextFlag.DEAD),
    REDSTONE_DISABLE("redstone-disable", new Object[]{"true", "false"}, ContextFlag.INTERACT),
    FLY_ENABLE("fly-enable", new Object[]{"true", "false"}, ContextFlag.ENTERED_RG);

    @Getter private final String idFlag;
    @Getter private final Object[] arguments;
    @Getter private final ContextFlag[] triggered;

    Flag(String idFlag, Object[] arguments, ContextFlag ...triggered) {
        this.idFlag = idFlag;
        this.arguments = arguments;
        this.triggered = triggered;
    }
}
