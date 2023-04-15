package ru.solomka.guard.command;

import lombok.Getter;
import org.bukkit.block.Block;
import ru.solomka.guard.command.module.GCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager {
    @Getter private static final List<GCommand> COMMAND_CONTAINER = new ArrayList<>();

    public static void init(GCommand...commands) {
        Arrays.stream(commands).forEach(c -> {
            try {
                new CommandBuilder(c).initСontrols().initAliases().build();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        COMMAND_CONTAINER.addAll(Arrays.stream(commands).collect(Collectors.toList()));
    }
}