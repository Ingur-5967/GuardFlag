package ru.solomka.guard.command;

import lombok.Getter;
import ru.solomka.guard.command.module.ECommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager {

    @Getter private static final List<ECommand> COMMAND_CONTAINER = new ArrayList<>();

    public static void init(ECommand ...commands) {
        Arrays.stream(commands).forEach(c -> new CommandBuilder(c).initСontrols().initAliases().build());
        COMMAND_CONTAINER.addAll(Arrays.stream(commands).collect(Collectors.toList()));
    }

    public ECommand getCommandOfSyntax(String syntaxCommand) {
        return COMMAND_CONTAINER.stream().filter(command -> command.getSyntax().contains(syntaxCommand)).findAny().orElse(null);
    }
}