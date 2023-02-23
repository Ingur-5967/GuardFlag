package ru.solomka.guard.core.flag.event;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Set;

public class RegionChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter private final Player player;
    @Getter private final Set<ProtectedRegion> fromRegion;
    @Getter private final Set<ProtectedRegion> toRegion;

    private boolean cancel;

    public RegionChangeEvent(Player player, Set<ProtectedRegion> fromRegion, Set<ProtectedRegion> toRegion) {
        this.player = player;
        this.fromRegion = fromRegion;
        this.toRegion = toRegion;
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
