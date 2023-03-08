package ru.solomka.guard.core.gui;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ru.solomka.guard.utils.GLogger;

import java.util.List;

@AllArgsConstructor
public class PlaceholderManager {

   @Getter private final String[] placeholders, replacement;

    @SuppressWarnings("unchecked")
    public <T> T getReplacedElement(T target) {

        if(placeholders.length != replacement.length) {
            GLogger.error(GLogger.EColor.RED,"Invalid length placeholders");
            return target;
        }

        for (int pI = 0; pI < placeholders.length; pI++) {
            if(target instanceof List<?>) {
                List<String> currentList = (List<String>) target;
                int fPI = pI;
                currentList.replaceAll(s -> s.replace(placeholders[fPI], replacement[fPI]));
            }
            else
                target = (T) target.toString().replace(placeholders[pI], replacement[pI]);
        }
        return target;
    }

}
