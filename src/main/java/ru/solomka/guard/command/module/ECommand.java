package ru.solomka.guard.command.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import ru.solomka.guard.command.module.enums.SenderType;
import ru.solomka.guard.utils.GLogger;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
        if(!(senderType == SenderType.PLAYER_OP ? SenderType.PLAYER_OP.getPredicate().test(sender) : SenderType.PLAYER.getPredicate().test(sender))) {
            sender.sendMessage(String.format("You're cannot execute this command, maybe you not '%s'", senderType.name()));
            return true;
        }

        if(permission != null && !((permission.equals("*") || permission.equals("op")) ? sender.isOp() : sender.hasPermission(permission))) {
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

        if (getToViewElementsWrapper()[args.length - 1] == null)
            return Collections.emptyList();

        return getTabsOfArgumentIndex(getToViewElementsWrapper(), args.length - 1);
    }
}