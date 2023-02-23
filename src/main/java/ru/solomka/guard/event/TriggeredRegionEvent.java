package ru.solomka.guard.event;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.listener.WorldGuardWorldListener;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.GFlagComponent;
import ru.solomka.guard.core.flag.enums.HarmType;
import ru.solomka.guard.core.flag.event.RegionEnteringEvent;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.event.RegionLeftEvent;
import ru.solomka.guard.core.flag.utils.GLogger;

import java.util.List;

public class TriggeredRegionEvent implements Listener {

    @EventHandler
    public void onEnteredRegion(RegionEnteringEvent event) throws InstantiationException, IllegalAccessException {

        Player player = event.getPlayer();

        ProtectedRegion region = event.getRegion();

        List<GFlagComponent<?, ?>> flags = new FlagManager().getFlagsInRegion(region.getId());

    }

    @EventHandler
    public void onExitedRegion(RegionLeftEvent event) throws InstantiationException, IllegalAccessException {
        Player player = event.getPlayer();

        ProtectedRegion region = event.getRegion();

        List<GFlagComponent<?, ?>> flags = new FlagManager().getFlagsInRegion(region.getId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHarmRegion(RegionHarmEvent event) {
        Player player = event.getPlayer();

        /*if (event.getHarmType() == HarmType.BREAK) {
            BlockBreakEvent bEvent = (BlockBreakEvent) event.getEvent();

            ProtectedRegion region = WorldGuardHelper.getRegionOfContainsBlock(bEvent.getBlock());

            if (region != null) {

                Flag<?> flag = WGBukkit.getPlugin().getFlagRegistry().get("BUILD");

                StateFlag stateFlag = (StateFlag) flag;

                region.setFlag(flag, player);
            }

            List<ItemStack> drops = (List<ItemStack>) bEvent.getBlock().getDrops();

            bEvent.getBlock().getDrops().clear();

            if (player.getGameMode() == GameMode.SURVIVAL && bEvent.isDropItems())
                drops.forEach(b -> bEvent.getBlock().getWorld().dropItem(bEvent.getBlock().getLocation(), b));

            bEvent.getBlock().setType(Material.AIR);


            if (bEvent.getBlock().getType() == Material.AIR || bEvent.getBlock() == null) {
                if (region != null && region.getMembers() != null)
                    region.getMembers().removePlayer(player.getUniqueId());
            }
        } else if (event.getHarmType() == HarmType.PLACE) {
            BlockPlaceEvent pEvent = (BlockPlaceEvent) event.getEvent();

            Location location = new Location(
                    player.getWorld(), pEvent.getBlock().getX(),
                    pEvent.getBlock().getY(), pEvent.getBlock().getZ(), pEvent.getBlock().getLocation().getYaw(),
                    pEvent.getBlock().getLocation().getPitch()
            );

            ProtectedRegion region = WorldGuardHelper.getRegionOfContainsBlock(location.getBlock());

            if (region != null) {
                region.getMembers().addPlayer(player.getUniqueId());
                GLogger.info(region.getMembers());
            }



            if(pEvent.getBlockPlaced().getType() != Material.AIR && pEvent.getBlockPlaced() != null) {
                if(region != null)
                    region.getMembers().removePlayer(player.getUniqueId());
            }
            //location.getBlock().setType(pEvent.getItemInHand().getType());

            //player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

        }

         */
    }
}