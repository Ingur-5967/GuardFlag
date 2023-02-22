package ru.solomka.guard.core.utils;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.block.Block;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.files.FileUtils;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.entity.GStateBlock;

import java.util.Map;

public class RegionValidator {

    public boolean isContainsFlag(String idRegion, String[] args) {
        Yaml file = new GRegionManager().getFileRegion(idRegion);
        return notNull(file.getString("Blocks." + args[0].toUpperCase() + ".State")) &&
                file.getString("Blocks." + args[0].toUpperCase() + ".State").equals(args[1].toLowerCase());
    }

    public GStateBlock getInfoOfContainsBlock(Block block) {

        GRegionManager gRegionManager = new GRegionManager();

        for (Map.Entry<String, ProtectedRegion> aMap : WorldGuardHelper.getRegionManager(block.getWorld()).getRegions().entrySet())
            if(aMap.getValue().contains(block.getX(), block.getY(), block.getZ()) &&
                    notNull(gRegionManager.getFileRegion(aMap.getKey()).getString("Blocks." + block.getType().name())))

                return new GStateBlock(aMap.getKey(), aMap.getValue());

        return null;
    }

    private static boolean notNull(Object value) {
        return value != null;
    }
}