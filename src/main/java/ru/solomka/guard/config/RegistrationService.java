package ru.solomka.guard.config;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import ru.solomka.guard.Main;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.utils.ServiceRegistrationBuilder;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.utils.GLogger;

import java.util.Arrays;

public class RegistrationService {

    public static void initConfigs(DirectorySource directoryType, String ...files) {
        if(Main.getInstance().getDataFolder() == null) Main.getInstance().getDataFolder().mkdir();
        for (String str : files) {
            if (directoryType != DirectorySource.NONE)
                new Yaml(directoryType.getType(), str + ".yml", true);
            else
                new Yaml(str + ".yml");
        }
    }

    public static void registrationCmd(CommandExecutor[] executors, TabCompleter[] completes, String ...commands) {
        boolean executorsSmall = executors.length == 1;
        for (int i = 0; i < executors.length; i++) {
            new ServiceRegistrationBuilder(executorsSmall ? commands[0] : commands[i])
                    .setExecutor(executorsSmall ? executors[0] : executors[i])
                    .setTabCompleter(executorsSmall ? completes[0] : completes[i])
                    .build();
        }
    }

    public static void registrationEvents(Listener ...listeners) {
        Arrays.stream(listeners).forEach(e -> Bukkit.getPluginManager().registerEvents(e, Main.getInstance()));
        GLogger.info("Listeners success <g>registration</res> ('" + listeners.length + "')");

    }
}