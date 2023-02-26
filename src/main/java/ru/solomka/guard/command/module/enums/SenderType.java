package ru.solomka.guard.command.module.enums;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public enum SenderType {
    PLAYER(Player.class),
    CONSOLE(ConsoleCommandSender.class),
    PLAYER_OP(Player.class);

    @Getter private final Class<? extends CommandSender> iClass;

    SenderType(Class<? extends CommandSender> iClass) {
        this.iClass = iClass;
    }
}
