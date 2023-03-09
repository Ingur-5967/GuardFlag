package ru.solomka.guard.core.flag.event.handler;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.enums.Harm;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.gui.GPlaceholder;

public class GuardEntryHarm implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreakBypass(BlockBreakEvent event) {
        GPlaceholder.sendMessageToPlayer(event.getPlayer(), "Hello, <r>world</res>");
        if (WorldGuardHelper.getRegionOfContainsBlock(event.getBlock()) != null)
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakHandler(BlockBreakEvent event) {
        Block block = event.getBlock();
        RegionHarmEvent regionHarmEvent = new RegionHarmEvent(event.getPlayer(), Harm.BREAK, event);
        if (WorldGuardHelper.getRegionOfContainsBlock(block) != null) {
            FlagManager.callEvent(regionHarmEvent);
            event.setCancelled(regionHarmEvent.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlaceBypass(BlockPlaceEvent event) {
        GPlaceholder.sendMessageToPlayer(event.getPlayer(), "Hello, <r>world</res>");
        if (WorldGuardHelper.getRegionOfContainsBlock(event.getBlockPlaced()) != null)
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlaceHandler(BlockPlaceEvent event) {
        RegionHarmEvent regionHarmEvent = new RegionHarmEvent(event.getPlayer(), Harm.PLACE, event);
        if (WorldGuardHelper.getRegionOfContainsBlock(event.getBlockPlaced()) != null) {
            FlagManager.callEvent(regionHarmEvent);
            event.setCancelled(regionHarmEvent.isCancelled());
        }
    }
}
