package ru.solomka.guard.command.impl;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.guard.command.module.ECommand;
import ru.solomka.guard.command.module.entity.TabViewCommand;
import ru.solomka.guard.command.module.enums.SenderType;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.gui.GUIManager;
import ru.solomka.guard.core.gui.module.impl.GuardMenu;

public class RegionFlagCommand extends ECommand<RegionFlagCommand> {

    public RegionFlagCommand() {
        super(
                SenderType.PLAYER,
                "drg",
                null,
                null,
                false,
                new Object[]{
                        new TabViewCommand(0, new Object[]{"flag", "info"}),
                        new TabViewCommand(1, new Object[]{"<inter-region-name>"}),
                        new TabViewCommand(2, new Object[]{"<inter-flag-name>"}),
                        new TabViewCommand(3, new Object[]{"<valid-argument>"}),
                        new TabViewCommand(4, new Object[]{"<argument-state>"})
                }
        );
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0) {
            player.sendMessage(getHelpCommand());
            return true;
        }

        RegionManager regionManager = WGBukkit.getRegionManager(player.getWorld());

        if (regionManager == null) return true;

        ProtectedRegion region;

        if(args[0].equalsIgnoreCase("flag")) {

            if(args.length < 4) {
                player.sendMessage(getHelpCommand());
                return true;
            }

            region = regionManager.getRegion(args[1]);

            if (region == null) {
                player.sendMessage("Регион не существует!");
                return true;
            }

            if (!player.isOp() && !region.getOwners().contains(player.getUniqueId())) {
                player.sendMessage("Вы не являетесь владельцем региона");
                return true;
            }

            if (Material.getMaterial(args[3].toUpperCase()) == null) {
                player.sendMessage("Введене некорректный материал");
                return true;
            }

            String state = args[4].toLowerCase();

            if (!state.equals("allow") && !state.equals("deny")) {
                player.sendMessage("Введен неверный параметр для флага! (Варианты: allow/deny)");
                return true;
            }

            String[] defArgs = {"controller", "params"};

            GFlag<?, ?> controller = FlagManager.getControllerOfId(args[2]);

            if(controller == null)
                throw new NullPointerException("Controller cannot be null!");

            Object[] defParams = {controller.getClass().getName().split("\\.")[6], args[4].toLowerCase()};

            for (int i = 0; i < defArgs.length; i++)
                new GRegionManager().createRegionFile(args[1]).set("flags." + args[2].toLowerCase() + "." + defArgs[i], defParams[i].toString());

            player.sendMessage("Успешно установлен флаг для региона " + args[1] + " (Material: " + args[3].toUpperCase() + ") значение " + args[4].toLowerCase());
            return true;
        }

        else if (args[0].equalsIgnoreCase("info")) {

            if(args.length < 2) {
                player.sendMessage(getHelpCommand());
                return true;
            }

            region = regionManager.getRegion(args[1]);

            if (region == null) {
                player.sendMessage("Регион не существует!");
                return true;
            }
            new GUIManager().callGUI(new GuardMenu(), player);
        }
        return true;
    }

    @Override
    public RegionFlagCommand getInstance() {
        return this;
    }

    @Override
    public String getHelpCommand() {
        return ">> Help command:\n/drg flag <inter-region-name> <inter-flag-name> <valid-argument> <argument-state>\n/drg info <inter-region-name>";
    }
}
