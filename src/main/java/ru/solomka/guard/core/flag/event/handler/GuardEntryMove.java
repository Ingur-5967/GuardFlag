package ru.solomka.guard.core.flag.event.handler;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.solomka.guard.core.utils.WorldGuardHelper;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.event.RegionEnteredEvent;
import ru.solomka.guard.core.flag.event.RegionMovingEvent;

public class GuardEntryMove implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getFrom().getBlock().getLocation().equals(event.getTo().getBlock().getLocation())) return;

        ProtectedRegion regionTo = WorldGuardHelper.getRegionOfContainsBlock(event.getTo().getBlock());
        ProtectedRegion regionFrom = WorldGuardHelper.getRegionOfContainsBlock(event.getFrom().getBlock());

        if (regionFrom == null && regionTo == null) return;

        FlagManager.callEvent(new RegionMovingEvent(player, event.getFrom(), event.getTo(), regionTo));

        if (regionFrom != null && WorldGuardHelper.isContainsInRegion(regionFrom, event.getFrom())) return;

        if (regionTo != null)
            FlagManager.callEvent(new RegionEnteredEvent(player, event.getFrom(), event.getTo(), regionTo));
    }
}
