package ru.solomka.guard.config;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import ru.solomka.guard.Main;
import ru.solomka.guard.config.enums.DirectorySource;
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

    public static void registrationEvents(Listener ...listeners) {
        Arrays.stream(listeners).forEach(e -> Bukkit.getPluginManager().registerEvents(e, Main.getInstance()));
        GLogger.info("Listeners success <g>registration</res> ('" + listeners.length + "')");

    }
}