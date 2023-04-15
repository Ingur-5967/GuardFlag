package ru.solomka.guard.core.flag.module.impl;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.lang.entity.Message;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.utils.WorldGuardHelper;
import ru.solomka.guard.core.flag.entity.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.flag.utils.FlagRoute;
import ru.solomka.guard.core.gui.tools.InventoryUtils;
import ru.solomka.guard.utils.GLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.*;

public class BuildBlockFlag extends GFlag<RegionHarmEvent> {

    public BuildBlockFlag() {
        super(Flag.BLOCK_BUILD.getIdFlag(), Flag.BLOCK_BUILD.getArgumentsToCommand());
    }

    @Override
    public void onEnable(RegionHarmEvent event) {

        PlayerInteractEvent interactEvent = (PlayerInteractEvent) event.getParent();

        Block block = interactEvent.getClickedBlock();

        Player player = event.getPlayer();

        ProtectedRegion region = WorldGuardHelper.getRegionOfContainsBlock(block);

        DefaultDomain members = region.getMembers();
        DefaultDomain owners = region.getOwners();

        Yaml file = new GRegionManager(region.getId()).getFileRegion();

        if (player.isOp() || (members.contains(player.getUniqueId()) || owners.contains(player.getUniqueId()))) {
            event.setCancelled(false);
            return;
        }

        if (!existsFlag(region.getId())) {
           player.sendMessage("Нет тут флага, олух");
           event.setCancelled(true);
            return;
        }

        Map<Material, String> states = new HashMap<>();

        List<String> params = FlagRoute.getParamsFlag(region.getId(), Flag.BLOCK_BUILD.getIdFlag());

        assert params != null;
        for (String paramHeader : params) {
            if (Flag.BLOCK_BUILD.getValidArguments().stream().map(f -> Material.getMaterial(f.toString()).name()).collect(Collectors.toList()).contains(paramHeader))
                continue;

            states.put(Material.getMaterial(paramHeader), file.getString("flags." + getIdFlag() + ".params." + paramHeader));
        }

        GLogger.info(event.getHarmType());

        if (states.containsKey(block.getType())) {
            for (Map.Entry<Material, String> aMap : states.entrySet())
                if (InventoryUtils.compareMaterials(aMap.getKey(), block.getType()))
                    event.setCancelled(!aMap.getValue().equals("allow"));
        } else {
            player.sendMessage("Блок вне списка!");
            event.setCancelled(true);
        }
    }
}