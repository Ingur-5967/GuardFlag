package ru.solomka.guard.core.flag.event.handler;

import com.sk89q.worldguard.bukkit.event.block.UseBlockEvent;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.enums.ActionBlock;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.event.RegionInteractBlockEvent;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.utils.WorldGuardHelper;

public class GuardEntryBlock implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractBypass(UseBlockEvent e) {
        if(WorldGuardHelper.getRegionOfContainsBlock(e.getBlocks().get(0)) != null)
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteractHandler(UseBlockEvent e) {

        ProtectedRegion region = WorldGuardHelper.getRegionOfContainsBlock(e.getBlocks().get(0));

        if(e.getOriginalEvent() == null) return;

        if(!(e.getOriginalEvent() instanceof PlayerInteractEvent)) return;

        if(!(e.getCause().getFirstEntity() instanceof Player) || e.getCause().getFirstPlayer() == null) return;

        PlayerInteractEvent interactEvent = (PlayerInteractEvent) e.getOriginalEvent();

        Player player = e.getCause().getFirstPlayer();

        Action action = interactEvent.getAction();

        if(region == null) {
            e.setCancelled(false);
            return;
        }

        GFlag.GMaterialFilter filter = GFlag.getFilter();

        if(action == Action.LEFT_CLICK_AIR || action == Action.RIGHT_CLICK_AIR) return;

        Event event = null;

        if(action == Action.RIGHT_CLICK_BLOCK && filter.canUse(interactEvent.getClickedBlock().getType()))
            event = new RegionInteractBlockEvent(player, interactEvent.getClickedBlock(), interactEvent.getAction(), region);
        else if(action == Action.RIGHT_CLICK_BLOCK && filter.canBuild(interactEvent.getClickedBlock().getType(), player.getInventory().getItemInMainHand().getType()))
            event = new RegionHarmEvent(player, ActionBlock.PLACE, region, interactEvent);
        else if(action == Action.LEFT_CLICK_BLOCK)
            event = new RegionHarmEvent(player, ActionBlock.BREAK, region, interactEvent);

        if(event == null) return;

        FlagManager.callEvent(event);

        e.setCancelled(((Cancellable) event).isCancelled());
    }
}