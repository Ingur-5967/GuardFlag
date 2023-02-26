package ru.solomka.guard.core.flag.event.handler;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.enums.world.HarmType;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;

public class GuardEntryHarm implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreakBypass(BlockBreakEvent event) {
        if (WorldGuardHelper.getRegionOfContainsBlock(event.getBlock()) != null)
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakHandler(BlockBreakEvent event) {
        Block block = event.getBlock();
        RegionHarmEvent regionHarmEvent = new RegionHarmEvent(event.getPlayer(), HarmType.BREAK, event);
        if (WorldGuardHelper.getRegionOfContainsBlock(block) != null) {
            FlagManager.callEvent(regionHarmEvent);
            event.setCancelled(regionHarmEvent.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlaceBypass(BlockPlaceEvent e) {
        if (WorldGuardHelper.getRegionOfContainsBlock(e.getBlockPlaced()) != null)
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlaceHandler(BlockPlaceEvent e) {
        RegionHarmEvent regionHarmEvent = new RegionHarmEvent(e.getPlayer(), HarmType.PLACE, e);
        if (WorldGuardHelper.getRegionOfContainsBlock(e.getBlockPlaced()) != null) {
            FlagManager.callEvent(regionHarmEvent);
            e.setCancelled(regionHarmEvent.isCancelled());
        }
    }
}
