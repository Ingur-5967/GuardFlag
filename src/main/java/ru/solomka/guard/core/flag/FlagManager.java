package ru.solomka.guard.core.flag;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import ru.solomka.guard.config.RegistrationService;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.flag.entity.GFlagComponent;
import ru.solomka.guard.core.flag.enums.ContextFlag;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.flag.utils.FlagRoute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlagManager {

    private static final List<GFlag<?, ?>> FLAG_CONTAINER = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public static <T extends Event> void callController(GFlag<?, ?> flag, T eventArgument) {
        ((GFlag<T, ?>) flag).onTrigger(eventArgument);
    }

    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public static void initCustomFlags(GFlag<?, ?> ...flags) {
        FLAG_CONTAINER.addAll(Arrays.stream(flags).collect(Collectors.toList()));
    }

    public List<GFlag<?, ?>> getGFlagsOf(ContextFlag contextFlag) {

        List<Flag> flags = Arrays.stream(Flag.values())
                .filter(f -> Arrays.asList(f.getTriggered()).contains(contextFlag))
                .collect(Collectors.toList());

        if(flags.isEmpty()) return null;

        return FLAG_CONTAINER.stream()
                .filter(f -> flags.stream().anyMatch(fl -> f.getIdFlag().equals(fl.getIdFlag())))
                .collect(Collectors.toList());
    }

    public static GFlag<?, ?> getControllerOfId(String idFlag) {
        return FLAG_CONTAINER.stream()
                .filter(f -> f.getIdFlag().equals(idFlag))
                .findAny().orElse(null);
    }
}