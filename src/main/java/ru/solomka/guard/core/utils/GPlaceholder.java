package ru.solomka.guard.core.utils;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.core.gui.module.entity.GPlaceholderEntry;
import ru.solomka.guard.utils.GLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GPlaceholder {

    @Getter
    private GPlaceholderEntry[] placeholders;

    public GPlaceholder(GPlaceholderEntry ...placeholders) {
        this.placeholders = placeholders;
    }

    public GPlaceholder() {}

    @SuppressWarnings("unchecked")
    public <T> T replaceElements(T target) {

        if(placeholders.length < 1) return target;

        for(GPlaceholderEntry placeholder : placeholders) {
            if (target instanceof List<?>) {
                List<String> current = (List<String>) target;
                current.replaceAll(s -> s.replace(placeholder.getReplacement(), String.valueOf(placeholder.getValue())));
            }
            else
                target = (T) target.toString().replace(placeholder.getReplacement(), String.valueOf(placeholder.getValue()));
        }
        return target;
    }

    private static String replaceTags(String current) {

        if (current == null) return "";

        StringBuilder replacedArguments = new StringBuilder();

        for (String argument : current.split(" ")) {

            String sPointColorTagOpen, sPointColorTagClose;

            if(argument.startsWith("<") && (argument.length() > 4 || argument.length() > GColor.RESET.getSyntax().length())) {
                sPointColorTagOpen = argument.substring(0, 3).toLowerCase();
                sPointColorTagClose = argument.substring(argument.length() - GColor.RESET.getSyntax().length());
            }
            else {
                replacedArguments.append(argument).append(" ");
                continue;
            }

            String fPointOpenTag = sPointColorTagOpen;
            GColor colorTagOpen = Arrays.stream(GColor.values()).filter(c -> c.getSyntax().equals(fPointOpenTag)).findAny().orElse(null);

            if (colorTagOpen != null)
                argument = argument.replace(sPointColorTagOpen, colorTagOpen.getColorCode());

            if(sPointColorTagClose.startsWith("</"))
                argument = argument.replace(sPointColorTagClose, GColor.RESET.getColorCode());

            replacedArguments.append(argument).append(" ");
        }
        return replacedArguments.toString();
    }

    public String getReplacedElementOfTags(String element) {
        return replaceTags(element);
    }

    public enum GColor {

        RED("<r>", "\u001b[31m"),
        GREEN("<g>", "\u001b[32m"),
        YELLOW("<y>", "\u001b[33m"),
        BLUE("<b>", "\u001b[34m"),
        WHITE("<w>", "\u001b[37m"),
        RESET("</res>", "\u001b[0m");

        @Getter
        private final String syntax;
        @Getter
        private final String colorCode;

        GColor(String syntax, String colorCode) {
            this.syntax = syntax;
            this.colorCode = colorCode;
        }
    }
}
