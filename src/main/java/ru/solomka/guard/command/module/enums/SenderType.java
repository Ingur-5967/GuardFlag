package ru.solomka.guard.command.module.enums;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.ServerOperator;

import java.util.function.Consumer;
import java.util.function.Predicate;

public enum SenderType {
    PLAYER(p -> p instanceof Player),
    CONSOLE(p -> p instanceof ConsoleCommandSender),
    PLAYER_OP(p -> (p instanceof Player && p.isOp()));

    @Getter private final Predicate<CommandSender> predicate;

    SenderType(Predicate<CommandSender> predicate) {
        this.predicate = predicate;
    }
}
