package ru.solomka.guard.core.flag.event.handler;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.event.RegionEnteringEvent;

public class GuardEntryMove implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerMoveEvent event) {
        if (WorldGuardHelper.getRegionOfContainsBlock(event.getTo().getBlock()) == null)
            return;

        Bukkit.getPluginManager().callEvent(new RegionEnteringEvent(event.getPlayer(), WorldGuardHelper.getRegionOfContainsBlock(event.getTo().getBlock())));
    }
}
