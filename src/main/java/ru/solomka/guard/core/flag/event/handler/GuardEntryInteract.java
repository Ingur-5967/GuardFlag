package ru.solomka.guard.core.flag.event.handler;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.event.RegionInteractItemsEvent;

public class GuardEntryInteract implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractBypass(PlayerInteractEvent event) {
        if (WorldGuardHelper.getRegionOfContainsBlock(event.getClickedBlock()) != null)
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteractHandler(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (WorldGuardHelper.getRegionOfContainsBlock(block) == null) return;

        Bukkit.getPluginManager().callEvent(new RegionInteractItemsEvent(event.getPlayer(), block, WorldGuardHelper.getRegionOfContainsBlock(block)));
    }
}