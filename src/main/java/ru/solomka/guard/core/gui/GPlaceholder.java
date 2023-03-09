package ru.solomka.guard.core.gui;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.utils.GLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GPlaceholder {

    @Getter
    private final String[] placeholders;
    @Getter
    private final String[] replacement;

    public GPlaceholder(String[] placeholders, String[] replacement) {
        this.placeholders = placeholders;
        this.replacement = replacement;
    }

    public GPlaceholder() {
        this(null, null);
    }

    public String replaceTags(String current) {

        if(current == null) return "";

        StringBuilder replacedArguments = new StringBuilder();

        for (String argument : current.split(" ")) {
            if (!argument.startsWith("<")) {
                replacedArguments.append(argument).append(" ");
                continue;
            }

            String sPointColorTagOpen = argument.substring(0, 3).toLowerCase();
            String sPointColorTagClose = argument.substring(argument.length() - 4).toLowerCase();

            GColor colorTagOpen = Arrays.stream(GColor.values()).filter(c -> c.getSyntax().equals(sPointColorTagOpen)).findAny().orElse(null);

            if (colorTagOpen == null)
                throw new IllegalArgumentException(String.format("Color tag (<OPEN_TAG>) of argument [%s] was not found", argument));

            if (!sPointColorTagClose.startsWith("</"))
                throw new IllegalArgumentException(String.format("Color tag (</CLOSE_TAG>) of argument [%s] was not found", argument));

            argument = argument.replace(sPointColorTagOpen, colorTagOpen.getColorCode()).replace(sPointColorTagClose, GColor.RESET.getColorCode());

            replacedArguments.append(argument).append(" ");
        }
        return replacedArguments.toString();
    }


    @SuppressWarnings("unchecked")
    public <T> T getReplacedElement(T target) {

        if (placeholders.length != replacement.length) {
            GLogger.error("Invalid length placeholders");
            return target;
        }

        for (int pI = 0; pI < placeholders.length; pI++) {
            if (target instanceof List<?>) {
                List<String> currentList = (List<String>) target;
                int fPI = pI;
                currentList.replaceAll(s -> s.replace(placeholders[fPI], replacement[fPI]));
            } else
                target = (T) target.toString().replace(placeholders[pI], replacement[pI]);
        }
        return target;
    }


    public enum GColor {

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

        GColor(String syntax, String colorCode) {
            this.syntax = syntax;
            this.colorCode = colorCode;
        }
    }
}
