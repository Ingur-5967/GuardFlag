package ru.solomka.guard.core.flag.event.handler;

import com.sk89q.worldguard.bukkit.WGBukkit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.enums.HarmType;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.utils.GLogger;

public class GuardEntryHarm implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHarmBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        RegionHarmEvent regionHarmEvent = new RegionHarmEvent(event.getPlayer(), HarmType.BREAK, event);

        if(WorldGuardHelper.getRegionOfContainsBlock(block) != null) {
            Bukkit.getPluginManager().callEvent(regionHarmEvent);
            event.setCancelled(regionHarmEvent.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHarmPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        RegionHarmEvent regionHarmEvent = new RegionHarmEvent(event.getPlayer(), HarmType.PLACE, event);

        if(WorldGuardHelper.getRegionOfContainsBlock(block) != null) {
            Bukkit.getPluginManager().callEvent(regionHarmEvent);
            event.setCancelled(regionHarmEvent.isCancelled());
        }
    }
}
