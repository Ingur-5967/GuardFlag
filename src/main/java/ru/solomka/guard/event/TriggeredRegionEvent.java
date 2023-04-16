package ru.solomka.guard.event;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.solomka.guard.config.utils.FileUtils;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionEnteredEvent;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.event.RegionInteractBlockEvent;
import ru.solomka.guard.core.flag.event.RegionMovingEvent;
import ru.solomka.guard.utils.GLogger;

public class TriggeredRegionEvent implements Listener {

    private final FlagManager flagManager = new FlagManager();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMovingRegion(RegionMovingEvent event) {
        if (flagManager.getGFlags(Flag.ContextFlag.MOVING) == null) return;
        flagManager.getGFlags(Flag.ContextFlag.MOVING).forEach(f -> FlagManager.callController(f, event));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEnteredRegion(RegionEnteredEvent event) {

        if(FileUtils.getDefaultCfg("config").getBoolean("region-entered-notification.action-bar")) {
            String format = FileUtils.getDefaultCfg("config").getString("region-entered-notification.format");

            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    new TextComponent(ChatColor.translateAlternateColorCodes('&', format.replace("%region%", event.getRegion().getId()))));
        }

        if (flagManager.getGFlags(Flag.ContextFlag.ENTERED_REGION) == null) return;
        flagManager.getGFlags(Flag.ContextFlag.ENTERED_REGION).forEach(f -> FlagManager.callController(f, event));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHarmRegion(RegionHarmEvent event) {
        switch (event.getHarmType()) {
            case BREAK: {
                if (flagManager.getGFlags(Flag.ContextFlag.BREAK) == null)
                    return;

                flagManager.getGFlags(Flag.ContextFlag.BREAK).forEach(f -> FlagManager.callController(f, event));
                break;
            }
            case PLACE: {
                if (flagManager.getGFlags(Flag.ContextFlag.PLACE) == null)
                    return;

                flagManager.getGFlags(Flag.ContextFlag.PLACE).forEach(f -> FlagManager.callController(f, event));
            }

            case INTERACT: {
                if (flagManager.getGFlags(Flag.ContextFlag.INTERACT) == null)
                    return;

                flagManager.getGFlags(Flag.ContextFlag.INTERACT).forEach(f -> FlagManager.callController(f, event));
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteractRegion(RegionInteractBlockEvent event) {
        if(flagManager.getGFlags(Flag.ContextFlag.INTERACT) == null)
            return;

        flagManager.getGFlags(Flag.ContextFlag.INTERACT).forEach(f -> FlagManager.callController(f, event));
    }
}