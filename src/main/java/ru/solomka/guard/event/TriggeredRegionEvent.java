package ru.solomka.guard.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.enums.ContextFlag;
import ru.solomka.guard.core.flag.event.RegionEnteredEvent;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.event.RegionLeftEvent;
import ru.solomka.guard.core.flag.event.RegionMovingEvent;
import ru.solomka.guard.core.flag.utils.GLogger;

public class TriggeredRegionEvent implements Listener {

    @EventHandler
    public void onMovingRegion(RegionMovingEvent event) {
        FlagManager flagManager = new FlagManager();
        if(flagManager.getGFlagsOf(ContextFlag.MOVING) == null) return;
        flagManager.getGFlagsOf(ContextFlag.MOVING).forEach(f -> FlagManager.callController(f, event));
    }

    @EventHandler
    public void onEnteredRegion(RegionEnteredEvent event) {
        FlagManager flagManager = new FlagManager();
        if(flagManager.getGFlagsOf(ContextFlag.ENTERED) == null) return;
        flagManager.getGFlagsOf(ContextFlag.ENTERED).forEach(f -> FlagManager.callController(f, event));
    }

    @EventHandler
    public void onExitedRegion(RegionLeftEvent event) {
        //todo
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHarmRegion(RegionHarmEvent event) {

        FlagManager flagManager = new FlagManager();

        switch (event.getHarmType()) {
            case BREAK: {
                if(flagManager.getGFlagsOf(ContextFlag.BREAK) == null) return;
                GLogger.info("point");
                flagManager.getGFlagsOf(ContextFlag.BREAK).forEach(f -> FlagManager.callController(f, event));
                break;
            }
            case PLACE: {
                if(flagManager.getGFlagsOf(ContextFlag.PLACE) == null) return;
                flagManager.getGFlagsOf(ContextFlag.PLACE).forEach(f -> FlagManager.callController(f, event));
                break;
            }
            case INTERACT_WITH_ITEM: {
                if(flagManager.getGFlagsOf(ContextFlag.INTERACT) == null) return;
                flagManager.getGFlagsOf(ContextFlag.INTERACT).forEach(f -> FlagManager.callController(f, event));
                break;
            }
        }
    }
}