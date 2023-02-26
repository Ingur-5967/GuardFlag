package ru.solomka.guard.command.module;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import ru.solomka.guard.command.module.entity.TabViewCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface ECommandHelper extends CommandExecutor, TabCompleter {
    @Override
    boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    @Override
    List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);

    String getHelpCommand();

    default List<String> getTabsOfArgumentIndex(Object[] wrapper, int index) {
        for (Object o : wrapper) {
            TabViewCommand complete = (TabViewCommand) o;
            if (complete.getIndex() == index)
                return Arrays.stream(complete.getToView()).map(String::valueOf).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}