package ru.solomka.guard.core.flag.event.handler;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.event.RegionEnteredEvent;
import ru.solomka.guard.core.flag.event.RegionMovingEvent;

public class GuardEntryMove implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (WorldGuardHelper.getRegionOfContainsBlock(event.getTo().getBlock()) == null)
            return;

        ProtectedRegion region = WorldGuardHelper.getRegionOfContainsBlock(event.getTo().getBlock());

        Bukkit.getPluginManager().callEvent(new RegionMovingEvent(player, event.getFrom(), event.getTo(), region));

        if(region.contains(event.getFrom().getBlock().getX(), event.getFrom().getBlock().getY(), event.getFrom().getBlock().getZ())) return;

        if(region.contains(event.getTo().getBlock().getX(), event.getTo().getBlock().getY(), event.getTo().getBlock().getZ()))
            Bukkit.getPluginManager().callEvent(new RegionEnteredEvent(player, event.getFrom(), event.getTo(), region));
    }
}
