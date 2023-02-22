package ru.solomka.guard.command;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import ru.solomka.guard.command.module.ECommand;

import java.util.Arrays;
import java.util.Collections;

public class CommandBuilder {

    @Getter private final ECommand<?> command;

    private final PluginCommand minecraftCommand;

    public CommandBuilder(ECommand<?> command) {
        this.command = command;
        minecraftCommand = Bukkit.getPluginCommand(command.getSyntax());
    }

    public CommandBuilder initСontrols() {
        minecraftCommand.setExecutor(command.getInstance());
        minecraftCommand.setTabCompleter(command.getInstance());
        return this;
    }

    public CommandBuilder initAliases() {
        minecraftCommand.setAliases(command.getAliases() == null ? Collections.emptyList() : Arrays.asList(command.getAliases()));
        return this;
    }

    public void build() {
        System.out.printf("Command '/%s' success registration\n", command.getSyntax());
    }
}