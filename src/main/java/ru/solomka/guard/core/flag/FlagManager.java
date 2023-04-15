package ru.solomka.guard.core.flag;

import com.sk89q.worldguard.bukkit.event.block.UseBlockEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.core.flag.entity.enums.Flag;
import ru.solomka.guard.core.flag.module.GFlag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlagManager {
    private static final List<GFlag<?>> FLAG_CONTAINER = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public static <T extends Event> void callController(@NotNull GFlag<?> flag, T eventArgument) {
        ((GFlag<T>) flag).onEnable(eventArgument);
    }

    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public static void initCustomFlags(GFlag<?> ...flags) {
        FLAG_CONTAINER.addAll(Arrays.stream(flags).collect(Collectors.toList()));
    }

    public List<GFlag<?>> getGFlags(Flag.ContextFlag contextFlag) {

        List<Flag> flags = Arrays.stream(Flag.values()).filter(f -> Arrays.asList(f.getTriggered()).contains(contextFlag)).collect(Collectors.toList());

        if(flags.isEmpty()) return null;

        return FLAG_CONTAINER.stream()
                .filter(f -> flags.stream().anyMatch(fl -> f.getIdFlag().equals(fl.getIdFlag())))
                .collect(Collectors.toList());
    }

    public static GFlag<?> getControllerOfId(String idFlag) {
        return FLAG_CONTAINER.stream()
                .filter(f -> f.getIdFlag().equals(idFlag)).findAny().orElse(null);
    }
}