package ru.solomka.guard.config.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.Main;
import ru.solomka.guard.config.Yaml;

import java.io.File;

public class FileUtils {

    @Contract("_ -> new")
    public static @NotNull Yaml getDefaultCfg(String file) {
        return new Yaml(new File(Main.getInstance().getDataFolder(), file + ".yml"));
    }

    @Contract("_, _ -> new")
    public static @NotNull Yaml getDirectoryFile(String directory, String file) {
        return new Yaml(new File(Main.getInstance().getDataFolder() + "/" + directory + "/" + file + ".yml"));
    }



}