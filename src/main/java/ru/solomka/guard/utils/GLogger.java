package ru.solomka.guard.utils;

import org.bukkit.Material;
import ru.solomka.guard.Main;
import ru.solomka.guard.core.gui.tools.GPlaceholder;

import java.util.*;

public class GLogger {

    public static void warn(Object... values) {
        logger("[WARNING]", values);
    }

    public static void debug(Object... values) {
        logger("[DEBUG]", values);
    }

    public static void info(Object... values) {
        logger("[INFO]", values);
    }

    public static void error(Object... values) {
        logger("[!!!EXCEPTION!!!]", values);
    }

    private static void logger(String prefix, Object... values) {
        Arrays.stream(values).map(String::valueOf).forEach(value -> Main.getInstance().getLogger().info(prefix + " >> " + new GPlaceholder().getReplacedElementOfTags(value)));
    }
}