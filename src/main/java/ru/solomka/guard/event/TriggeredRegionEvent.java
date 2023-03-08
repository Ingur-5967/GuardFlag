package ru.solomka.guard.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionEnteredEvent;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.event.RegionLeftEvent;
import ru.solomka.guard.core.flag.event.RegionMovingEvent;

import java.lang.reflect.InvocationTargetException;

public class TriggeredRegionEvent implements Listener {

    private final FlagManager flagManager = new FlagManager();

    @EventHandler
    public void onMovingRegion(RegionMovingEvent event) {
        if(flagManager.getGFlagsOf(Flag.ContextFlag.MOVING) == null) return;
        flagManager.getGFlagsOf(Flag.ContextFlag.MOVING).forEach(f -> {
            try {
                FlagManager.callController(f, event, "onEnable");
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @EventHandler
    public void onEnteredRegion(RegionEnteredEvent event) {
        if(flagManager.getGFlagsOf(Flag.ContextFlag.ENTERED_REGION) == null) return;
        flagManager.getGFlagsOf(Flag.ContextFlag.ENTERED_REGION).forEach(f -> {
            try {
                FlagManager.callController(f, event, "onEnable");
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHarmRegion(RegionHarmEvent event) {
        switch (event.getHarmType()) {
            case BREAK: {
                if(flagManager.getGFlagsOf(Flag.ContextFlag.BREAK) == null) return;
                flagManager.getGFlagsOf(Flag.ContextFlag.BREAK).forEach(f -> {
                    try {
                        FlagManager.callController(f, event, "onEnable");
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                             InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
                break;
            }
            case PLACE: {
                if(flagManager.getGFlagsOf(Flag.ContextFlag.PLACE) == null) return;
                flagManager.getGFlagsOf(Flag.ContextFlag.PLACE).forEach(f -> {
                    try {
                        FlagManager.callController(f, event, "onEnable");
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                             InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
                break;
            }
            case INTERACT_WITH_ITEM: {
                if(flagManager.getGFlagsOf(Flag.ContextFlag.INTERACT) == null) return;
                flagManager.getGFlagsOf(Flag.ContextFlag.INTERACT).forEach(f -> {
                    try {
                        FlagManager.callController(f, event, "onEnable");
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                             InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }
}