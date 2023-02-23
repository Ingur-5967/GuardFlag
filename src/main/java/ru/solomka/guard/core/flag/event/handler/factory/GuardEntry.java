package ru.solomka.guard.core.flag.event.handler.factory;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.SessionManager;
import com.sk89q.worldguard.session.handler.Handler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.event.RegionChangeEvent;
import ru.solomka.guard.core.flag.event.RegionEnteringEvent;
import ru.solomka.guard.core.flag.event.RegionLeftEvent;
import ru.solomka.guard.core.flag.utils.GLogger;

import java.util.Set;

public class GuardEntry extends Handler implements Listener {

    public GuardEntry() {
        super(new Session(new SessionManager(WorldGuardHelper.getGuardInstance())));
    }


    @Override
    public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        PluginManager plm = Bukkit.getPluginManager();
        for(ProtectedRegion region : entered) {
            RegionEnteringEvent regionEnteringEvent = new RegionEnteringEvent(player, region);
            plm.callEvent(regionEnteringEvent);
            if(regionEnteringEvent.isCancelled()) return false;
        }

        for(ProtectedRegion region : exited) {
            RegionLeftEvent regionLeftEvent = new RegionLeftEvent(player, region);
            plm.callEvent(regionLeftEvent);
            if(regionLeftEvent.isCancelled()) return false;
        }
        return true;
    }
}