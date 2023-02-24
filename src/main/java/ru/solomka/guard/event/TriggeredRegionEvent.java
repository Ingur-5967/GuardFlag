package ru.solomka.guard.event;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.GFlagComponent;
import ru.solomka.guard.core.flag.enums.ContextFlag;
import ru.solomka.guard.core.flag.event.RegionEnteredEvent;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.event.RegionLeftEvent;
import ru.solomka.guard.core.flag.event.RegionMovingEvent;

import java.util.List;
import java.util.Optional;

public class TriggeredRegionEvent implements Listener {

    @EventHandler
    public void onMovingRegion(RegionMovingEvent event) {
        FlagManager flagManager = new FlagManager();
        if(flagManager.getGFlagsOf(ContextFlag.MOVING) == null) return;
        FlagManager.callController(FlagManager.getControllerOfId(flagManager.getGFlagsOf(ContextFlag.MOVING).getIdFlag()), event);
    }

    @EventHandler
    public void onEnteredRegion(RegionEnteredEvent event) {
        FlagManager flagManager = new FlagManager();
        if(flagManager.getGFlagsOf(ContextFlag.ENTERED) == null) return;
        FlagManager.callController(FlagManager.getControllerOfId(flagManager.getGFlagsOf(ContextFlag.ENTERED).getIdFlag()), event);
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
                FlagManager.callController(FlagManager.getControllerOfId(flagManager.getGFlagsOf(ContextFlag.BREAK).getIdFlag()), event);
                break;
            }
            case PLACE: {
                if(flagManager.getGFlagsOf(ContextFlag.PLACE) == null) return;
                FlagManager.callController(FlagManager.getControllerOfId(flagManager.getGFlagsOf(ContextFlag.PLACE).getIdFlag()), event);
                break;
            }
            case INTERACT_WITH_ITEM: {
                if(flagManager.getGFlagsOf(ContextFlag.INTERACT) == null) return;
                FlagManager.callController(FlagManager.getControllerOfId(flagManager.getGFlagsOf(ContextFlag.INTERACT).getIdFlag()), event);
                break;
            }
        }
    }
}