package ru.solomka.guard.core.flag.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;

import java.util.function.Predicate;

@AllArgsConstructor
public abstract class GFlag<E extends Event> {

    @Getter private final String idFlag;
    @Getter private final Object[] allowParams;

    public void onTrigger(E event) {}

    public String getFailedMessage() { return ""; }

    public <P> boolean checkArgument(P value, Predicate<P> predicate) {
        return predicate.test(value);
    }
}