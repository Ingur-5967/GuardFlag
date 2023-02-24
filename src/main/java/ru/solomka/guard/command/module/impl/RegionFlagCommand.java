package ru.solomka.guard.command.module.impl;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.guard.Main;
import ru.solomka.guard.command.module.ECommand;
import ru.solomka.guard.command.module.entity.TabViewCommand;
import ru.solomka.guard.command.module.enums.SenderType;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.flag.utils.GLogger;
import ru.solomka.guard.core.gui.GUIManager;
import ru.solomka.guard.core.gui.module.impl.GuardMenu;
import ru.solomka.guard.core.gui.module.impl.ViewRegionsMenu;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class RegionFlagCommand extends ECommand<RegionFlagCommand> {

    public RegionFlagCommand() {
        super(
                SenderType.PLAYER,
                "drg",
                null,
                null,
                false,
                new Object[]{
                        new TabViewCommand(0, new Object[]{"flag", "info", "regions"}),
                        new TabViewCommand(1, new Object[]{"<inter-region-name>"}),
                        new TabViewCommand(2, new Object[]{"<inter-flag-name>"}),
                        new TabViewCommand(3, new Object[]{"<argument:state> OR <argument-state>"}),
                }
        );
    }

    // /drg flag/info/regions <rg-name> <flag-name> <argument:state>

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

        GRegionManager gRegionManager = new GRegionManager();

        if(args[0].equalsIgnoreCase("flag")) {

            if(args.length < 4) {
                player.sendMessage(getHelpCommand());
                return true;
            }

            region = regionManager.getRegion(args.length < 1 ? "" : args[1]);

            if (region == null) {
                player.sendMessage("Регион не существует!");
                return true;
            }

            if (!player.isOp() && !region.getOwners().contains(player.getUniqueId())) {
                player.sendMessage("Вы не являетесь владельцем региона");
                return true;
            }

            String flagName = args.length < 2 ? "" : args[2].toLowerCase();

            Flag targetFlag = Arrays.stream(Flag.values()).filter(f -> f.getIdFlag().equals(flagName)).findAny().orElse(null);

            if(targetFlag == null) {
                player.sendMessage("Флаг не найден!");
                return true;
            }

            String state = args.length < 3 ? "" : args[3].toLowerCase();


            if(state.equals("clear")) {
                player.sendMessage("Вы успешно очистили параметры флага");
                gRegionManager.getFileRegion(region.getId()).set("flags." + flagName + ".params", false, new String[]{"[]"});
                return true;
            }

            String argument = Arrays.stream(targetFlag.getArguments()).filter(t -> String.valueOf(t).equals(state) || t.toString().contains(":")).map(String::valueOf).findAny().orElse(null);

            StringBuilder builder = null;

            if (argument == null) {

                builder = new StringBuilder();

                for (int i = 0; i < targetFlag.getArguments().length; i++)
                    builder.append(targetFlag.getArguments()[i]).append(i == targetFlag.getArguments().length - 1 ? "" : "/");

                player.sendMessage("Введен неверный параметр для флага! (Варианты: " + (builder.toString().contains(":") ? "...<argument:allow/deny>" : builder) + ")");
                return true;
            }

            // <argument:state> more OR <argument>

            if(gRegionManager.getFileRegion(region.getId()) == null)
                gRegionManager.createRegionFile(region.getId());

            Yaml file = gRegionManager.getFileRegion(region.getId());

            String[] defArgs = {"controller", "params"};

            GFlag<?, ?> controller = FlagManager.getControllerOfId(args[2]);

            if(controller == null)
                throw new NullPointerException("Controller cannot be null!");

            Object[] defParams;

            if(argument.contains(":")) {

                builder = new StringBuilder();

                for (int i = 3; i < args.length; i++) {

                    if(args[i] == null) continue;

                    if(!args[i].contains(":"))
                        throw new IllegalArgumentException("Invalid arguments for flag!");

                    String rArgument = args[i];

                    String name = rArgument.split(":")[0].toUpperCase();
                    String value = rArgument.split(":")[1].toLowerCase();

                    String currentLabel = file.getStringList("flags." + flagName + ".params").stream()
                            .filter(s -> s.split(":")[0].equals(name) && s.split(":")[1].equals(value)).findAny().orElse(null);

                    if(currentLabel != null)
                        player.sendMessage("Обнаружены повторы! Затронутый элемент <ARGUMENT and STATE>\n" +
                                ">> Elements: " + currentLabel + "\n (Такой параметр уже есть в регионе)\n");

                    if(!value.equals("allow") && !value.equals("deny")) {
                        player.sendMessage("Invalid value for flag!");
                        return true;
                    }
                    builder.append(name).append(":").append(value).append(" ");
                }
            }

            if(builder != null && builder.length() > 1)
                defParams = new Object[]{controller.getClass().getName().split("\\.")[7], builder.toString().split(" ")};
            else
                defParams = new Object[]{controller.getClass().getName().split("\\.")[7], args[3]};

            for (int i = 0; i < defArgs.length; i++)
                gRegionManager.getFileRegion(region.getId()).set("flags." + flagName + "." + defArgs[i], i == defArgs.length - 1, new Object[]{defParams[i]});

            /*String[] defArgs = {"controller", "params"};

            GFlag<?, ?> controller = FlagManager.getControllerOfId(args[2]);

            if(controller == null)
                throw new NullPointerException("Controller cannot be null!");

            Object[] defParams = {controller.getClass().getName().split("\\.")[6], args[4].toLowerCase()};

            for (int i = 0; i < defArgs.length; i++)
                new GRegionManager().createRegionFile(args[1]).set("flags." + args[2].toLowerCase() + "." + defArgs[i], defParams[i].toString());


             */
            //player.sendMessage("Успешно установлен флаг для региона " + args[1] + " (Material: " + args[3].toUpperCase() + ") значение " + args[4].toLowerCase());
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

        else if(args[0].equalsIgnoreCase("regions")) {


            File dir = new File(Main.getInstance().getDataFolder() + File.separator + DirectorySource.DATA);

            if(dir.isDirectory()) {
                if(dir.listFiles() == null) return true;

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
