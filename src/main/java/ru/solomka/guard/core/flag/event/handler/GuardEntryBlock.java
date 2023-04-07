package ru.solomka.guard.core.flag.event.handler;

import com.sk89q.worldguard.bukkit.event.block.UseBlockEvent;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.enums.ActionBlock;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.event.RegionInteractBlockEvent;
import ru.solomka.guard.core.gui.tools.InventoryUtils;
import ru.solomka.guard.core.utils.WorldGuardHelper;

public class GuardEntryBlock implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractBypass(UseBlockEvent event) {
        if (WorldGuardHelper.getRegionOfContainsBlock(event.getBlocks().get(0)) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractHandler(UseBlockEvent event) {
        Block block = event.getBlocks().get(0);

        Player target = event.getCause().getFirstPlayer();

        if(event.getOriginalEvent() == null) return;

        PlayerInteractEvent interact = (PlayerInteractEvent) event.getOriginalEvent();

        ProtectedRegion region = WorldGuardHelper.getRegionOfContainsBlock(block);

        Event e = null;

        if(target == null || (block == null || region == null)) return;

        ItemStack item = target.getInventory().getItemInMainHand();

        Action currentAction = interact.getAction();

        if(target.getTargetBlock(null, 6) != null) {

            Block targetBlock = target.getTargetBlock(null, 6);

            if(!InventoryUtils.compareMaterials(item.getType(), Material.AIR)) {
                e = new RegionHarmEvent(
                        target,
                        ActionBlock.PLACE,
                        region,
                        new BlockPlaceEvent(block, block.getState(), block, target.getInventory().getItemInMainHand(), target, true, EquipmentSlot.HAND)
                );
            }

            if(e == null && (InventoryUtils.compareMaterials(item.getType(), Material.AIR) || !InventoryUtils.compareMaterials(item.getType(), Material.AIR)) && currentAction == Action.LEFT_CLICK_BLOCK) {
                e = new RegionHarmEvent(
                        target, ActionBlock.BREAK, region,
                        new BlockBreakEvent(block, target.getPlayer())
                );
            }

            if(e == null) {
                e = new RegionInteractBlockEvent(
                        target, block,
                        interact.getAction(), region
                );
            }
        }

        if(e == null) return;

        FlagManager.callEvent(e);
        event.setCancelled(((Cancellable) e).isCancelled());
    }
}