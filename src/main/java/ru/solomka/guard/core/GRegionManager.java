package ru.solomka.guard.core;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.block.Block;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.files.FileUtils;
import ru.solomka.guard.core.entity.GRegionBlock;
import ru.solomka.guard.core.entity.GStateBlock;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.GFlagComponent;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.flag.utils.FlagRoute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GRegionManager {

    public Yaml createRegionFile(String idRegion) {
        
        Yaml data = null;
        
        if (getFileRegion(idRegion) == null) {
            data = FileUtils.getDirectoryFile(DirectorySource.DATA.getType(), "region_" + idRegion);
            data.set("flags", "[]");
        }

        return getFileRegion(idRegion) == null ? data : getFileRegion(idRegion);
    }

    public GFlagComponent<?, ?> getParamsFlag(String idRegion, String idFlag) {
        return new GFlagComponent<>(idFlag, FlagRoute.getParamsFlag(idRegion, idFlag), FlagManager.getControllerOfId(idFlag));
    }

    public Yaml getFileRegion(String idRegion) {
        return FileUtils.getDirectoryFile(DirectorySource.DATA.getType(), "region_" + idRegion);
    }
}
