package ru.solomka.guard.event;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.GFlagComponent;
import ru.solomka.guard.core.flag.enums.HarmType;
import ru.solomka.guard.core.flag.event.RegionEnteredEvent;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.event.RegionLeftEvent;
import ru.solomka.guard.core.flag.event.RegionMovingEvent;

import java.util.List;

public class TriggeredRegionEvent implements Listener {

    @EventHandler
    public void onMovingRegion(RegionMovingEvent event) {
        event.getPlayer().sendMessage("Detect moving in region!");
    }

    @EventHandler
    public void onEnteredRegion(RegionEnteredEvent event) {
        Player player = event.getPlayer();

        if (!player.isOp()) {
            player.sendMessage("Запрещено входить в регион");

            player.teleport(new Location(
                            event.getFrom().getBlock().getWorld(),
                            event.getFrom().getBlock().getX(), event.getFrom().getBlock().getY(), event.getFrom().getBlock().getZ(),
                            player.getLocation().getYaw(), player.getLocation().getPitch()));
        }
    }

    @EventHandler
    public void onExitedRegion(RegionLeftEvent event) {
        Player player = event.getPlayer();

        ProtectedRegion region = event.getRegion();

        List<GFlagComponent<?, ?>> flags = new FlagManager().getFlagsInRegion(region.getId());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onHarmRegion(RegionHarmEvent event) {
        Player player = event.getPlayer();


        if (event.getHarmType() == HarmType.BREAK) {
            BlockBreakEvent bEvent = (BlockBreakEvent) event.getEvent();

            List<ItemStack> drops = (List<ItemStack>) bEvent.getBlock().getDrops();

            bEvent.getBlock().getDrops().clear();

            if (player.getGameMode() != GameMode.CREATIVE && bEvent.isDropItems())
                drops.forEach(d -> bEvent.getBlock().getWorld().dropItem(bEvent.getBlock().getLocation(), d));


            bEvent.getBlock().setType(Material.AIR);

            if (bEvent.getBlock().getType() == Material.AIR)
                event.setCancelled(true);

        } else if (event.getHarmType() == HarmType.PLACE) { // FIXME: 23.02.2023
            BlockPlaceEvent pEvent = (BlockPlaceEvent) event.getEvent();
            pEvent.getBlockPlaced().setType(Material.CHEST);

        }
    }
}