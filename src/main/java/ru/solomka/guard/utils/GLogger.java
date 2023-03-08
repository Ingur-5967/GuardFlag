package ru.solomka.guard.utils;

import lombok.Getter;
import ru.solomka.guard.Main;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
        Arrays.stream(values).map(String::valueOf).forEach(value -> {

            StringBuilder replacedArguments = new StringBuilder();

            for (String argument : value.split(" ")) {

                if (!argument.startsWith("<")) {
                    replacedArguments.append(argument).append(" ");
                    continue;
                }

                String sPointColorTagOpen = argument.substring(0, 3).toLowerCase();
                String sPointColorTagClose = argument.substring(argument.length() - 4).toLowerCase();

                EColor colorTagOpen = Arrays.stream(EColor.values()).filter(c -> c.getSyntax().equals(sPointColorTagOpen)).findAny().orElse(null);

                if (colorTagOpen == null)
                    throw new IllegalArgumentException(String.format("Color tag (<OPEN_TAG>) of argument [%s] was not found", argument));

                if(!sPointColorTagClose.startsWith("</"))
                    throw new IllegalArgumentException(String.format("Color tag (</CLOSE_TAG>) of argument [%s] was not found", argument));

                argument = argument.replace(sPointColorTagOpen, colorTagOpen.getColorCode()).replace(sPointColorTagClose, EColor.RESET.getColorCode());

                replacedArguments.append(argument).append(" ");
            }
            Main.getInstance().getLogger().info(prefix + " >> " + replacedArguments);
        });
    }

    public enum EColor {

        RED("<r>", "\u001b[31m"),
        GREEN("<g>", "\u001b[32m"),
        YELLOW("<y>", "\u001b[33m"),
        BLUE("<b>", "\u001b[34m"),
        WHITE("<w>", "\u001b[37m"),
        RESET("", "\u001b[0m");

        @Getter
        private final String syntax;
        @Getter
        private final String colorCode;

        EColor(String syntax, String colorCode) {
            this.syntax = syntax;
            this.colorCode = colorCode;
        }
    }
}