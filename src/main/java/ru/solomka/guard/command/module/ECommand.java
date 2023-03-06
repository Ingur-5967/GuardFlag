package ru.solomka.guard.command.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.command.module.enums.SenderType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
public abstract class ECommand implements ECommandHelper {

    @Getter private final SenderType senderType;
    @Getter private final String syntax;
    @Getter @Setter private String permission;
    @Getter private final String[] aliases;
    @Getter private final Object[] toViewElementsWrapper;

    public abstract boolean execute(CommandSender sender, String[] args) throws InstantiationException, IllegalAccessException, IOException;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!validExecutor(sender, s -> senderType == SenderType.PLAYER_OP ? s.getClass().equals(senderType.getIClass()) && s.isOp() : s.getClass().equals(senderType.getIClass()))) {
            sender.sendMessage(String.format("You're cannot execute this command, maybe you not '%s'", senderType.name()));
            return true;
        }

        if(permission != null && !validExecutor(sender, s -> permission.equals("*") || permission.equals("op") ? s.isOp() : s.hasPermission(permission))) {
            sender.sendMessage("You're cannot execute this command, maybe you don't have permission!");
            return true;
        }

        try {
            return execute(sender, args);
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if ((args.length - 1) > getToViewElementsWrapper().length)
            return Collections.emptyList();

        if (getToViewElementsWrapper().length == 1)
            return getTabsOfArgumentIndex(getToViewElementsWrapper(), 0);

        if (getToViewElementsWrapper()[args.length - 1] == null)
            return Collections.emptyList();

        return getTabsOfArgumentIndex(getToViewElementsWrapper(), args.length - 1);
    }

    public boolean validExecutor(CommandSender sender, @NotNull Predicate<CommandSender> predicate) {
        return predicate.test(sender);
    }
}