package ru.solomka.guard.core.event;

import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.entity.GStateBlock;
import ru.solomka.guard.core.utils.RegionValidator;

public class BlockTriggeredEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        RegionValidator regionValidator = new RegionValidator();
        GStateBlock gStateBlock = regionValidator.getInfoOfContainsBlock(block);

        GRegionManager gRegionManager = new GRegionManager();

        if (gStateBlock == null || regionValidator.isContainsFlag(gStateBlock.getIdRegion(), new String[]{block.getType().name(), "allow"})
                || gStateBlock.getRegion().getOwners().contains(event.getPlayer().getUniqueId()))
            return;

        event.getPlayer().sendMessage("Владелец региона запретил взаимодействовать с данным блоком");
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        RegionValidator regionValidator = new RegionValidator();
        GStateBlock gStateBlock = regionValidator.getInfoOfContainsBlock(block);

        if (gStateBlock == null || regionValidator.isContainsFlag(gStateBlock.getIdRegion(), new String[]{block.getType().name(), "allow"})
                || gStateBlock.getRegion().getOwners().contains(event.getPlayer().getUniqueId()))
            return;

        event.getPlayer().sendMessage("Владелец региона запретил взаимодействовать с данным блоком");
        event.setCancelled(true);
    }
}