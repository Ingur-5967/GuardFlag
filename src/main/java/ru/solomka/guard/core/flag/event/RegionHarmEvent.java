package ru.solomka.guard.core.flag.event;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import ru.solomka.guard.core.flag.entity.enums.ActionBlock;


public class RegionHarmEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter private final Player player;
    @Getter private final ActionBlock harmType;
    @Getter private final ProtectedRegion region;
    @Getter private final BlockEvent event;

    private boolean cancel;

    public RegionHarmEvent(Player player, ActionBlock harmType, ProtectedRegion region, BlockEvent event) {
        this.player = player;
        this.harmType = harmType;
        this.region = region;
        this.event = event;
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
