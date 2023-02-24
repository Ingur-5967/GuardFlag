package ru.solomka.guard.command.module;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public interface ECommandHelper extends CommandExecutor, TabCompleter {
    @Override
    boolean onCommand(CommandSender sender, Command command, String label, String[] args);
    @Override
    List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);
    String getHelpCommand();
}
