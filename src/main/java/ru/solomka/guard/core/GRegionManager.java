package ru.solomka.guard.core;

import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.files.FileUtils;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.GFlagComponent;
import ru.solomka.guard.core.flag.utils.FlagRoute;

import java.io.IOException;

public class GRegionManager {

    public void createRegionFile(String idRegion) throws IOException {
        if (getFileRegion(idRegion) == null) {
            Yaml data = FileUtils.getDirectoryFile(DirectorySource.DATA.getType(), "region_" + idRegion);
            data.set("flags", "[]");
            data.set("world", "[]");
        }
    }

    public GFlagComponent<?, ?> getFlagComponent(String idRegion, String idFlag) {
        return new GFlagComponent<>(idFlag, FlagRoute.getParamsFlag(idRegion, idFlag), FlagManager.getControllerOfId(idFlag));
    }

    public Yaml getFileRegion(String idRegion) {
        return FileUtils.getDirectoryFile(DirectorySource.DATA.getType(), "region_" + idRegion);
    }
}
