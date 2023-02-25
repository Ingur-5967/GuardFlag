package ru.solomka.guard.core;

import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;
import ru.solomka.guard.Main;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.files.FileUtils;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.GFlagComponent;
import ru.solomka.guard.core.flag.utils.FlagRoute;
import ru.solomka.guard.core.flag.utils.GLogger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GRegionManager {

    public static void checkAllRegions() {

        File dir = new File(Main.getInstance().getDataFolder() + File.separator + DirectorySource.DATA.getType());

        if(dir.exists() && dir.isDirectory()) {

            if(dir.listFiles() == null) return;

            List<File> files = Arrays.stream(Objects.requireNonNull(dir.listFiles())).collect(Collectors.toList());

            for(File f : files) {

                if(!f.getName().contains("_")) return;

                String regionName = f.getName().split("_")[1].split("\\.")[0];

                Yaml file = FileUtils.getDirectoryFile(DirectorySource.DATA.getType(), f.getName().split("\\.yml")[0]);

                if(file.getString("world") == null || Bukkit.getWorld(file.getString("world")) == null)
                    f.delete();
                else {
                    String nameWorld = FileUtils.getDirectoryFile(DirectorySource.DATA.getType(), f.getName()).getString("world");

                    RegionManager regionManager = WorldGuardHelper.getRegionManager(Bukkit.getWorld(nameWorld));

                    if (regionManager.getRegion(regionName) == null) {
                        f.delete();
                    }

                }
            }
            GLogger.info("'{regions}' regions successfully passed verification".replace("{regions}", String.valueOf(Objects.requireNonNull(dir.listFiles()).length)));
        }
    }

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
