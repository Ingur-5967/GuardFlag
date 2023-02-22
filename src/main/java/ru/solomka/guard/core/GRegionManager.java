package ru.solomka.guard.core;

import org.bukkit.Material;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.files.FileUtils;
import ru.solomka.guard.core.entity.GRegionBlock;

import java.util.ArrayList;
import java.util.List;

public class GRegionManager {

    public Yaml createRegionFile(String idRegion) {
        
        Yaml data = null;
        
        if (getFileRegion(idRegion) == null) {
            data = FileUtils.getDirectoryFile(DirectorySource.DATA.getType(), "region_" + idRegion);
            data.set("Blocks", "[]");
        }

        return getFileRegion(idRegion) == null ? data : getFileRegion(idRegion);
    }

    // TODO
    public List<GRegionBlock> getAllStatesBlocks(String idRegion) {
        if (getFileRegion(idRegion) == null)
            createRegionFile(idRegion);

        Yaml data = getFileRegion(idRegion);

        List<GRegionBlock> regionBlocks = new ArrayList<>();

        for (Material material : Material.values()) {
            if (data.getString("Blocks." + material.name() + ".State") == null) continue;

            regionBlocks.add(
                    new GRegionBlock(
                            material,
                            data.getString("Blocks." + material.name() + ".State")
                    )
            );
        }

        return regionBlocks;
    }

    public Yaml getFileRegion(String idRegion) {
        return FileUtils.getDirectoryFile(DirectorySource.DATA.getType(), "region_" + idRegion);
    }
}
