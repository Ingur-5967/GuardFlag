package ru.solomka.guard.core.gui;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import ru.solomka.guard.core.flag.utils.GLogger;

import java.util.List;

@Data @AllArgsConstructor
public class PlaceholderManager<T> {

    private final String[] placeholders, replacement;
    @Setter private T target;

    @SuppressWarnings("unchecked")
    public T getReplacedElement() {

        if(placeholders.length != replacement.length) {
            GLogger.info("Invalid length placeholders");
            return target;
        }

        for (int pI = 0; pI < placeholders.length; pI++) {
            if(target instanceof List<?>) {
                List<String> currentList = (List<String>) target;
                int fPI = pI;
                currentList.replaceAll(s -> s.replace(placeholders[fPI], replacement[fPI]));
                setTarget((T) currentList);
            }
            else
                setTarget((T) target.toString().replace(placeholders[pI], replacement[pI]));
        }
        return target;
    }

}
