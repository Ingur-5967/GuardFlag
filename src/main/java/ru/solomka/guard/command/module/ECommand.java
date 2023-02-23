package ru.solomka.guard.command.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ru.solomka.guard.command.module.entity.CheckState;
import ru.solomka.guard.command.module.entity.TabViewCommand;
import ru.solomka.guard.command.module.enums.SenderType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public abstract class ECommand<T extends ECommand<?>> implements ECommandHelper {

    private final SenderType senderType;
    private final String syntax;
    private final String permission;
    private final String[] aliases;

    private final boolean sortingArguments;
    private final Object[] toViewElementsWrapper;

    public abstract boolean execute(CommandSender sender, String[] args) throws InstantiationException, IllegalAccessException;

    public abstract T getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CheckState executorCheckState = validExecutor(sender);

        if (!executorCheckState.isValid()) {
            if (executorCheckState.getReason() == CheckState.CallReason.INSTANCE_OF)
                sender.sendMessage(String.format("You're cannot execute this command, maybe you not '%s'", senderType.name()));

            else if (executorCheckState.getReason() == CheckState.CallReason.PERMISSION)
                sender.sendMessage(String.format("You're cannot execute this command, because you don't have permission '%s'", permission));

            return true;
        }
        try {
            return execute(sender, args);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        System.out.println(toView((args.length - 1)));

        if ((args.length - 1) > getToViewElementsWrapper().length)
            return Collections.emptyList();

        if (getToViewElementsWrapper().length == 1)
            return sortingArguments ? toSort(args[0], 0) : toView(0);

        if (getToViewElementsWrapper()[args.length - 1] == null)
            return Collections.emptyList();

        return sortingArguments
                ? toSort(args[args.length - 1], args.length - 1)
                : toView(args.length - 1);

    }

    public List<String> toView(int index) {
        for (Object view : toViewElementsWrapper) {
            TabViewCommand tabViewCommand = (TabViewCommand) view;
            if (tabViewCommand.getIndex() == index)
                return Arrays.stream(tabViewCommand.getToView()).map(String::valueOf).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // FIXME: 22.02.2023 
    public List<String> toSort(String value, int index) {

        List<String> values = new ArrayList<>();

        for (Object view : toViewElementsWrapper) {
            TabViewCommand tabViewCommand = (TabViewCommand) view;

            for (Object tab : tabViewCommand.getToView()) {
                if (!(tab instanceof List<?>))
                    values.add(tab.toString().toLowerCase().equalsIgnoreCase(value) ? tab.toString() : "Nothing");
                else
                    for (Object inter : (List<?>) tab)
                        values.add(inter.toString().toLowerCase().equalsIgnoreCase(value) ? inter.toString() : "Nothing");
            }
        }
        return (value == null || values.size() <= 0 || value.equals("")) ?
                Arrays.stream(((TabViewCommand) toViewElementsWrapper[index]).getToView()).map(String::valueOf).collect(Collectors.toList()) : values;
    }

    public CheckState validExecutor(CommandSender sender) {
        switch (senderType) {
            case CONSOLE:
                return new CheckState(sender instanceof ConsoleCommandSender, CheckState.CallReason.INSTANCE_OF);
            case PLAYER: {
                if (permission != null && !permission.equals(""))
                    return new CheckState(sender.hasPermission(permission), CheckState.CallReason.PERMISSION);

                return new CheckState(sender instanceof Player, CheckState.CallReason.INSTANCE_OF);
            }
            case PLAYER_OP:
                return new CheckState(sender instanceof Player && sender.isOp(), CheckState.CallReason.PERMISSION_AND_INSTANCE_OF);
        }
        return new CheckState(false, CheckState.CallReason.OTHER);
    }
}