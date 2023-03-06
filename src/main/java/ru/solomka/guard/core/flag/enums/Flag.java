package ru.solomka.guard.core.flag.enums;

import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Flag {

    TNT_EXPLOSION("tnt-explosion", new Object[]{"allow", "deny"}, null, ContextFlag.EXPLOSION),
    BLOCK_BUILD("block-build", new Object[]{":"}, Arrays.stream(Material.values()).map(String::valueOf).collect(Collectors.toList()), ContextFlag.BREAK, ContextFlag.PLACE),
    SAVE_ITEMS("save-items", new Object[]{"true", "false"}, null, ContextFlag.DEAD),
    REDSTONE_DISABLE("redstone-disable", new Object[]{"true", "false"}, null, ContextFlag.INTERACT),
    FLY_ENABLE("fly-enable", new Object[]{"true", "false"}, null, ContextFlag.ENTERED_REGION, ContextFlag.LEFT_REGION);

    @Getter private final String idFlag;
    @Getter private final Object[] argumentsToCommand;
    @Getter private final List<?> validArguments;
    @Getter private final ContextFlag[] triggered;

    Flag(String idFlag, Object[] argumentsToCommand, List<?> validArguments, ContextFlag ...triggered) {
        this.idFlag = idFlag;
        this.argumentsToCommand = argumentsToCommand;
        this.validArguments = validArguments;
        this.triggered = triggered;
    }
}
