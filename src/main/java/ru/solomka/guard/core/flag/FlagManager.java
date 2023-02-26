package ru.solomka.guard.core.flag;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import ru.solomka.guard.Main;
import ru.solomka.guard.config.RegistrationService;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.flag.entity.GFlagComponent;
import ru.solomka.guard.core.flag.enums.ContextFlag;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.flag.utils.FlagRoute;
import ru.solomka.guard.core.flag.utils.GLogger;

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
        RegistrationService.registrationEvents(flags);
        FLAG_CONTAINER.addAll(Arrays.stream(flags).collect(Collectors.toList()));
    }

    public List<GFlagComponent<?, ?>> getFlagsInRegion(String idRegion) {
        List<GFlagComponent<?, ?>> flags = new ArrayList<>();

        Yaml file = new GRegionManager().getFileRegion(idRegion);

        for(Flag flag : Flag.values()) {
            if (file.getString("flags." + flag.getIdFlag()) == null) continue;
            flags.add(new GFlagComponent<>(
                    flag.getIdFlag(),
                    FlagRoute.getParamsFlag(idRegion, flag.getIdFlag()),
                    FlagManager.getControllerOfId(flag.getIdFlag()))
            );
        }
        return flags;
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

    public GFlag<?, ?> getGFlag(String idRegion, String idFlag) {
        return FLAG_CONTAINER.stream()
                .filter(c -> c.getCurrentFlag(idRegion).getId().equals(idFlag))
                .findAny().orElse(null);
    }

    public GFlagComponent<?, ?> getFlag(String idRegion, String idFlag) {
        return FLAG_CONTAINER.stream().map(f -> f.getCurrentFlag(idRegion))
                .filter(currentFlag -> currentFlag.getId().equals(idFlag))
                .findAny().orElse(null);
    }

    public static GFlag<?, ?> getControllerOfId(String idFlag) {
        return FLAG_CONTAINER.stream()
                .filter(f -> f.getIdFlag().equals(idFlag))
                .findAny().orElse(null);
    }
}