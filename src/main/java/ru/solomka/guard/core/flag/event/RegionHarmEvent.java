package ru.solomka.guard.core.flag.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import ru.solomka.guard.core.flag.enums.world.HarmType;


public class RegionHarmEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter private final Player player;
    @Getter private final HarmType harmType;
    @Getter private final BlockEvent event;

    private boolean cancel;

    public RegionHarmEvent(Player player, HarmType harmType, BlockEvent event) {
        this.player = player;
        this.harmType = harmType;
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
