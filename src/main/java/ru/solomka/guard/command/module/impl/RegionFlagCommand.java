package ru.solomka.guard.command.module.impl;

import com.sk89q.worldguard.bukkit.WGBukkit;
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
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.gui.GUIManager;
import ru.solomka.guard.core.gui.module.impl.GuardMenu;
import ru.solomka.guard.core.gui.module.impl.ViewRegionsMenu;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RegionFlagCommand extends ECommand<RegionFlagCommand> {

    public RegionFlagCommand() {
        super(
                SenderType.PLAYER,
                "drg",
                "drg.usage",
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
    public boolean execute(CommandSender sender, String[] args) throws IOException {
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(getHelpCommand());
            return true;
        }

        RegionManager regionManager = WorldGuardHelper.getRegionManager(player.getWorld());

        if (regionManager == null) return true;

        ProtectedRegion region;

        GRegionManager gRegionManager = new GRegionManager();

        if (args[0].equalsIgnoreCase("flag")) {

            if (args.length < 4) {
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

            String flagName = args[2].toLowerCase();

            Flag targetFlag = Arrays.stream(Flag.values()).filter(f -> f.getIdFlag().equals(flagName)).findAny().orElse(null);

            if (targetFlag == null) {
                player.sendMessage("Флаг не найден!");
                return true;
            }

            String state = args[3].toLowerCase();

            if (state.equals("clear")) {
                player.sendMessage("Вы успешно очистили параметры флага");
                gRegionManager.getFileRegion(region.getId()).set("flags." + flagName + ".params", "");
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

            if (gRegionManager.getFileRegion(region.getId()) == null)
                gRegionManager.createRegionFile(region.getId());

            Yaml file = gRegionManager.getFileRegion(region.getId());

            GFlag<?> controller = FlagManager.getControllerOfId(args[2]);

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
            file.set("flags." + flagName + ".controller", controller.getClass().getName().split("\\.")[7]);

            if (builder != null && builder.length() > 1) {
                for (String label : builder.toString().split(" "))
                    file.set("flags." + flagName + ".params." + label.split(":")[0], label.split(":")[1]);
            } else file.set("flags." + flagName + ".params", args[3]);

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
        } else if (args[0].equalsIgnoreCase("regions")) {


            File dir = new File(Main.getInstance().getDataFolder() + File.separator + DirectorySource.DATA.getType());

            if (dir.isDirectory()) {
                if (dir.listFiles() == null) return true;

                //TODO

                List<File> files = Arrays.stream(Objects.requireNonNull(dir.listFiles())).collect(Collectors.toList());

                new GUIManager().callGUI(new ViewRegionsMenu(), player);
            }
        }
        return true;
    }

    @Override
    public RegionFlagCommand getInstance() {
        return this;
    }

    @Override
    public String getHelpCommand() {
        return ">> Help command:\n/drg flag <inter-region-name> <inter-flag-name> (<argument:state> OR <argument-state>)\n" +
                "/drg info <inter-region-name>\n" +
                "/drg regions";
    }
}
