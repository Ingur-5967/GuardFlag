package ru.solomka.guard.core.flag.module.impl;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.flag.utils.FlagRoute;
import ru.solomka.guard.core.gui.tools.InventoryUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class BuildBlockFlag extends GFlag<RegionHarmEvent> {

    public BuildBlockFlag() {
        super(Flag.BLOCK_BUILD.getIdFlag(), Flag.BLOCK_BUILD.getArgumentsToCommand());
    }

    @Override
    public void onEnable(RegionHarmEvent event) {

        Block block = event.getEvent().getBlock();
        Player player = event.getPlayer();
        ProtectedRegion region = WorldGuardHelper.getRegionOfContainsBlock(block);

        Yaml file = new GRegionManager().getFileRegion(region.getId());


        if(!containsFlag(region.getId())) {
            player.sendMessage(getFailedMessage());
            event.setCancelled(true);
            return;
        }

        Map<Material, String> states = new HashMap<>();

        List<String> params = FlagRoute.getParamsFlag(region.getId(), Flag.BLOCK_BUILD.getIdFlag());

        assert params != null;
        for(String paramHeader : params) {

            if(!checkArgument(paramHeader, hMaterial -> Flag.BLOCK_BUILD.getValidArguments().stream()
                    .map(f -> Material.getMaterial(f.toString()).name()).collect(Collectors.toList()).contains(hMaterial)))
                continue;

            states.put(Material.getMaterial(paramHeader), file.getString("flags." + getIdFlag() + ".params." + paramHeader));
        }

        if(states.containsKey(block.getType())) {
            for (Map.Entry<Material, String> aMap : states.entrySet())
                if (InventoryUtils.compareMaterials(aMap.getKey(), block.getType()))
                    event.setCancelled(!aMap.getValue().equals("allow"));
        }
        else {
            player.sendMessage(getFailedMessage());
            event.setCancelled(true);
        }
    }

    @Override
    public <Q extends Event> void onDisable(Q event) {}

    @Override
    public String getFailedMessage() {
        return translateAlternateColorCodes('&', "&7Вы не можете &c&lсовершить&7 данное действие в чужом регионе");
    }
}
