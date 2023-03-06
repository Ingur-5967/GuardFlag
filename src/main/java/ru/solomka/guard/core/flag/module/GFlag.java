package ru.solomka.guard.core.flag.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import ru.solomka.guard.core.flag.utils.FlagRoute;

import java.util.function.Predicate;

@AllArgsConstructor
public abstract class GFlag<E extends Event> {

    @Getter private final String idFlag;
    @Getter private final Object[] allowParams;

    public void onEnable(E event) {}
    public <Q extends Event> void onDisable(Q event) {}

    public boolean containsFlag(String regionId) {
        return FlagRoute.isExistsFlag(regionId, idFlag) && FlagRoute.getParamsFlag(regionId, idFlag) != null;
    }

    public String getFailedMessage() { return ""; }

    public <P> boolean checkArgument(P value, Predicate<P> predicate) {
        return predicate.test(value);
    }
}