package ru.solomka.guard.config.utils;

import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import ru.solomka.guard.Main;

public class ServiceRegistrationBuilder {

    @Getter
    private final String commandName;

    private final PluginCommand pluginCommand;

    public ServiceRegistrationBuilder(String commandName) {
        this.commandName = commandName;
        pluginCommand = Main.getInstance().getCommand(commandName);
    }

    public ServiceRegistrationBuilder setExecutor(CommandExecutor executor) {
        pluginCommand.setExecutor(executor);
        return this;
    }

    public ServiceRegistrationBuilder setTabCompleter(TabCompleter completer) {
        pluginCommand.setTabCompleter(completer);
        return this;
    }

    public void build() {
       // if(pluginCommand.isRegistered())
         //   Messages.logger("COMMAND-INIT", "Command '" + commandName + "' has been success registration");
        //else
        //    Messages.logger("COMMAND-INIT", "Error registering the command '" + commandName + "'");
    }
}
