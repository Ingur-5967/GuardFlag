package ru.solomka.guard.core.flag.event;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class RegionInteractBlockEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter private final Player player;
    @Getter private final Block targetBlock;
    @Getter private final Action action;
    @Getter private final ProtectedRegion region;

    private boolean cancel;

    public RegionInteractBlockEvent(Player player, Block targetBlock, Action action, ProtectedRegion region) {
        this.player = player;
        this.targetBlock = targetBlock;
        this.action = action;
        this.region = region;
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
