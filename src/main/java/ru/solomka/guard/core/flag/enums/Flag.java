package ru.solomka.guard.core.flag.enums;

import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Flag {

    TNT_EXPLOSION("tnt-explosion", new Object[]{"true", "false"}, Arrays.asList("true", "false"), ContextFlag.EXPLOSION),
    BLOCK_BUILD("block-build", new Object[]{":"}, Arrays.stream(Material.values()).map(String::valueOf).collect(Collectors.toList()), ContextFlag.BREAK, ContextFlag.PLACE),
    SAVE_ITEMS("save-items", new Object[]{"true", "false"}, Arrays.asList("true", "false"), ContextFlag.DEAD),
    REDSTONE_CONTROLLER("redstone", new Object[]{"true", "false"}, Arrays.asList("true", "false"), ContextFlag.INTERACT),
    FLY_ENABLE("fly-enable", new Object[]{"true", "false"}, Arrays.asList("true", "false"), ContextFlag.ENTERED_REGION, ContextFlag.LEFT_REGION);

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

    public enum ContextFlag {
        BREAK,
        INTERACT,
        EXPLOSION,
        PLACE,
        MOVING,
        DEAD,
        LEFT_REGION,
        ENTERED_REGION
    }
}
