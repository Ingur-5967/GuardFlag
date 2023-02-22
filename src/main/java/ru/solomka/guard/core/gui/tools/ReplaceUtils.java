package ru.solomka.guard.core.gui.tools;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.print.DocFlavor;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class ReplaceUtils {
    @Contract("_ -> param1")
    public static @NotNull List<String> getColoredList(@NotNull List<String> list) {
        list.replaceAll(s -> translateAlternateColorCodes('&', s));
        return list;
    }
}