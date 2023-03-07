package ru.solomka.guard.core.flag.event;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class RegionRedstoneEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter private final Player executor;
    @Getter private final ProtectedRegion currentRegion;
    @Getter private final Block triggeredBlock;

    @Getter private boolean cancel;

    public RegionRedstoneEvent(Player executor, ProtectedRegion currentRegion, Block triggeredBlock) {
        this.executor = executor;
        this.currentRegion = currentRegion;
        this.triggeredBlock = triggeredBlock;
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