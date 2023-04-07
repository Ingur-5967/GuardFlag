package ru.solomka.guard.core.flag.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.core.flag.utils.FlagRoute;

import java.util.function.Predicate;

@AllArgsConstructor
public abstract class GFlag<E extends Event> implements Reportable {

    @Getter private final String idFlag;
    @Getter private final Object[] allowParams;

    public abstract void onEnable(E event);

    public boolean existsFlag(String regionId) {
        return FlagRoute.isExistsFlag(regionId, idFlag) && FlagRoute.getParamsFlag(regionId, idFlag) != null;
    }
}