package ru.solomka.guard.core.flag.event;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegionMovingEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter private final Player player;

    @Getter private final ProtectedRegion region;

    @Getter private final Location from, to;

    private boolean cancel;

    public RegionMovingEvent(Player player, Location from, Location to, ProtectedRegion region) {
        this.player = player;
        this.region = region;
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }


    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
