package ru.solomka.guard.command;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import ru.solomka.guard.command.module.GCommand;
import ru.solomka.guard.utils.GLogger;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;

public class CommandBuilder {

    @Getter private final GCommand command;
    @Getter private final PluginCommand minecraftCommand;

    public CommandBuilder(GCommand command) {
        this.command = command;
        minecraftCommand = Bukkit.getPluginCommand(command.getSyntax());
    }

    public CommandBuilder initСontrols() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        minecraftCommand.setExecutor(command.getClass().getDeclaredConstructor().newInstance());
        minecraftCommand.setTabCompleter(command.getClass().getDeclaredConstructor().newInstance());
        return this;
    }

    public CommandBuilder initAliases() {
        minecraftCommand.setAliases(command.getAliases() == null ? Collections.emptyList() : Arrays.asList(command.getAliases()));
        return this;
    }

    public void build() {
        GLogger.info("Command <y>'/" + command.getSyntax() + "'</res> success <g>registration</res>");
    }
}