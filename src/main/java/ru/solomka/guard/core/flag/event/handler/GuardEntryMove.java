package ru.solomka.guard.core.flag.event.handler;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

        Location to = event.getTo();
        Location from = event.getFrom();

        if (WorldGuardHelper.getRegionOfContainsBlock(to.getBlock()) == null && WorldGuardHelper.getRegionOfContainsBlock(from.getBlock()) == null)
            return;

        ProtectedRegion regionTo = WorldGuardHelper.getRegionOfContainsBlock(to.getBlock());

        ProtectedRegion regionFrom = WorldGuardHelper.getRegionOfContainsBlock(from.getBlock());

        Bukkit.getPluginManager().callEvent(new RegionMovingEvent(player, event.getFrom(), event.getTo(), regionTo));

        if (regionFrom != null && WorldGuardHelper.isContainsInRegion(regionFrom, from)) return;

        if (regionTo != null && WorldGuardHelper.isContainsInRegion(regionTo, to))
            Bukkit.getPluginManager().callEvent(new RegionEnteredEvent(player, event.getFrom(), event.getTo(), regionTo));
    }
}
