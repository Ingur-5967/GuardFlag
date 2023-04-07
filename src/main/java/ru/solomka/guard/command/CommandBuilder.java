package ru.solomka.guard.command;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import ru.solomka.guard.command.module.ECommand;
import ru.solomka.guard.config.RegistrationService;
import ru.solomka.guard.utils.GLogger;

import java.util.Arrays;
import java.util.Collections;

public class CommandBuilder {

    @Getter private final ECommand command;
    @Getter private final PluginCommand minecraftCommand;

    public CommandBuilder(ECommand command) {
        this.command = command;
        minecraftCommand = Bukkit.getPluginCommand(command.getSyntax());
    }


    public CommandBuilder initСontrols() throws InstantiationException, IllegalAccessException {
        minecraftCommand.setExecutor(command.getClass().newInstance());
        minecraftCommand.setTabCompleter(command.getClass().newInstance());
        return this;
    }

    public CommandBuilder initAliases() {
        minecraftCommand.setAliases(command.getAliases() == null ? Collections.emptyList() : Arrays.asList(command.getAliases()));
        return this;
    }

    public static void onEna() {

    }

    public void build() {
        GLogger.info("Command <y>'/" + command.getSyntax() + "'</res> success <g>registration</res>");
    }
}