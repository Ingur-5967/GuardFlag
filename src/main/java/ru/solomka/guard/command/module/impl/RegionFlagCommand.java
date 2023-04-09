package ru.solomka.guard.command.module.impl;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.guard.Main;
import ru.solomka.guard.command.module.ECommand;
import ru.solomka.guard.command.module.entity.TabViewCommand;
import ru.solomka.guard.command.module.enums.SenderType;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.scoreboard.GScoreboard;
import ru.solomka.guard.core.utils.WorldGuardHelper;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.enums.Flag;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.gui.GUIManager;
import ru.solomka.guard.core.gui.module.impl.GuardMenu;
import ru.solomka.guard.utils.GLogger;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RegionFlagCommand extends ECommand {

    public RegionFlagCommand() {
        super(
                SenderType.PLAYER,
                "drg",
                null,
                null,
                new Object[]{
                        new TabViewCommand(0, new Object[]{"flag", "info", "regions"}),
                        new TabViewCommand(1, new Object[]{"<inter-region-name>"}),
                        new TabViewCommand(2, new Object[]{"<inter-flag-name>"}),
                        new TabViewCommand(3, new Object[]{"<argument:state> OR <argument-state>"}),
                }
        );
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(getHelpCommand());
            return true;
        }

        RegionManager regionManager = WorldGuardHelper.getRegionManager(player.getWorld());

        if (regionManager == null) return true;

        ProtectedRegion region;

        GRegionManager gRegionManager;

        if(args[0].equalsIgnoreCase("tp")) {
            if(args.length < 2) {
                player.sendMessage(getHelpCommand());
                return true;
            }

            region = regionManager.getRegion(args[1]);

            if (region == null) {
                player.sendMessage("Регион не существует!");
                return true;
            }
            player.teleport(WorldGuardHelper.getCenterRegionLocation(region));
        } else if (args[0].equalsIgnoreCase("flag")) {

            new GScoreboard().setupToPlayer(player, GScoreboard.Builder.builder().initObjective("&cRegion info").initScores(
                    GScoreboard.EMPTY_ARGUMENT, "&7&l> &fРегион: &7[&6some_name&7]", "&7&l> &fВладелец: &7[&asome_name&7]",
                    GScoreboard.EMPTY_ARGUMENT
            ));

            if (args.length < 4) {
                player.sendMessage(getHelpCommand());
                return true;
            }

            region = regionManager.getRegion(args[1]);

            if (region == null) {
                player.sendMessage("Регион не существует!");
                return true;
            }

            gRegionManager = new GRegionManager(region.getId());

            if (!player.isOp() && !region.getOwners().contains(player.getUniqueId())) {
                player.sendMessage("Вы не являетесь владельцем региона");
                return true;
            }

            // /drg flag region flag_name value

            Flag targetFlag = Arrays.stream(Flag.values()).filter(f -> f.name().equals(args[2].toUpperCase())).findAny().orElse(null);

            if (targetFlag == null) {
                player.sendMessage("Флаг не найден!");
                return true;
            }

            String flagState = Flag.valueOf(args[2].toUpperCase()).getIdFlag();
            String state = args[3].toLowerCase();

            if (state.equals("clear")) {
                player.sendMessage("Вы успешно очистили параметры флага");
                gRegionManager.getFileRegion().set("flags." + Flag.valueOf(args[2].toUpperCase()).getIdFlag() + ".params", "");
                return true;
            }

            String argument = Arrays.stream(targetFlag.getArgumentsToCommand()).filter(t -> String.valueOf(t).equals(state) || t.toString().contains(":")).map(String::valueOf).findAny().orElse(null);

            StringBuilder builder = null;

            if (argument == null) {

                builder = new StringBuilder();

                for (int i = 0; i < targetFlag.getArgumentsToCommand().length; i++)
                    builder.append(targetFlag.getArgumentsToCommand()[i]).append(i == targetFlag.getArgumentsToCommand().length - 1 ? "" : "/");

                player.sendMessage("Введен неверный параметр для флага! (Варианты: " + (builder.toString().contains(":") ? "...<argument:allow/deny>" : builder) + ")");
                return true;
            }

            if (gRegionManager.getFileRegion() == null)
                gRegionManager.createRegionFile();

            Yaml file = gRegionManager.getFileRegion();

            GFlag<?> controller = FlagManager.getControllerOfId(flagState);

            if (controller == null)
                throw new NullPointerException("Controller cannot be null!");

            if (argument.contains(":")) {

                builder = new StringBuilder();

                for (int i = 3; i < args.length; i++) {

                    if (args[i] == null) continue;

                    if (!args[i].contains(":"))
                        throw new IllegalArgumentException("Invalid arguments for flag!");

                    String rArgument = args[i];

                    String name = rArgument.split(":")[0].toUpperCase();
                    String value = rArgument.split(":")[1].toLowerCase();

                    if (!value.equals("allow") && !value.equals("deny")) {
                        player.sendMessage("Invalid value for flag!");
                        return true;
                    }
                    builder.append(name).append(":").append(value).append(" ");
                }
            }

            Location location = WorldGuardHelper.getCenterRegionLocation(region);

            if (location == null || location.getWorld() == null) return true;

            file.set("world", location.getWorld().getName());
            file.set("flags." + flagState + ".controller", controller.getClass().getName().split("\\.")[7]);

            if (builder != null && builder.length() > 1) {
                for (String label : builder.toString().split(" "))
                    file.set("flags." + flagState + ".params." + label.split(":")[0], label.split(":")[1]);
            } else file.set("flags." + flagState + ".params", args[3]);

            player.sendMessage("Флаг установлен!");

        } else if (args[0].equalsIgnoreCase("info")) {
            if (args.length < 2) {
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
    public String getHelpCommand() {
        return ">> Help command:\n/drg flag <inter-region-name> <inter-flag-name> (<argument:state> OR <argument-state>)\n" +
                "/drg info <inter-region-name>\n" +
                "/drg regions";
    }
}
