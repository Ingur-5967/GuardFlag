package ru.solomka.guard.core.flag.module.impl;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.GFlagComponent;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionHarmEvent;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.flag.utils.FlagRoute;
import ru.solomka.guard.core.flag.utils.GLogger;
import ru.solomka.guard.core.gui.tools.InventoryUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BuildBlockFlag extends GFlag<RegionHarmEvent, BuildBlockFlag> {

    public BuildBlockFlag() {
        super(Flag.BLOCK_BUILD.getIdFlag(), Flag.BLOCK_BUILD.getArgumentsToCommand());
    }

    @Override
    public void onTrigger(RegionHarmEvent event) {

        Block block = event.getEvent().getBlock();
        ProtectedRegion region = WorldGuardHelper.getRegionOfContainsBlock(block);

        Yaml file = new GRegionManager().getFileRegion(region.getId());

        GLogger.info("execute");

        if(!FlagRoute.isExistsFlag(region.getId(), Flag.BLOCK_BUILD.getIdFlag()))
            return;

        List<String> params = FlagRoute.getParamsFlag(region.getId(), Flag.BLOCK_BUILD.getIdFlag());

        if(params == null)
            return;

        Map<Material, String> states = new HashMap<>();

        for(String paramHeader : params) {

            if(!checkArgument(paramHeader, hMaterial -> Flag.BLOCK_BUILD.getValidArguments().stream()
                    .map(f -> Material.getMaterial(f.toString())).collect(Collectors.toList()).contains(Material.getMaterial(hMaterial))))
                continue;

            states.put(Material.getMaterial(paramHeader), file.getString("flags." + getIdFlag() + ".params." + paramHeader));
        }

        for(Map.Entry<Material, String> aMap : states.entrySet()) {
            if(InventoryUtils.compareMaterials(aMap.getKey(), block.getType())) {
                event.getPlayer().sendMessage("Владелец региона запретил взаимодействовать с данным блоком");
                event.setCancelled(true);
            }
        }
    }

    @Override
    public BuildBlockFlag getInstance() {
        return this;
    }
}
