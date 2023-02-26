package ru.solomka.guard.core;

import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.files.FileUtils;

import java.io.IOException;

public class GRegionManager {

    public void createRegionFile(String idRegion) {
        if (getFileRegion(idRegion) == null) {
            Yaml data = FileUtils.getDirectoryFile(DirectorySource.DATA.getType(), "region_" + idRegion);
            data.set("flags", "[]");
            data.set("world", "[]");
        }
    }

    public Yaml getFileRegion(String idRegion) {
        return FileUtils.getDirectoryFile(DirectorySource.DATA.getType(), "region_" + idRegion);
    }
}
