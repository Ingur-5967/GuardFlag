package ru.solomka.guard.core.flag.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ru.solomka.guard.core.flag.entity.GFlagComponent;
import ru.solomka.guard.core.flag.module.entity.GFlagHelper;
import ru.solomka.guard.core.flag.utils.FlagRoute;

import java.util.function.Predicate;

@AllArgsConstructor
public abstract class GFlag<C extends Event, T extends GFlag<?, ?>> extends GFlagHelper<C> {

    @Getter private final String idFlag;
    @Getter private final Object[] allowParams;

    @Override
    public void onTrigger(C event) {
        super.onTrigger(event);
    }

    @Override
    public void onTrigger(Player player, C event) { super.onTrigger(event); }

    public abstract T getInstance();

    public abstract String getFailedMessage();

    public GFlagComponent<?, ?> getCurrentFlag(String idRegion) {
        return new GFlagComponent<>(idFlag, FlagRoute.getParamsFlag(idRegion, idFlag), this);
    }

    public <P> boolean checkArgument(P value, Predicate<P> predicate) {
        return predicate.test(value);
    }
}