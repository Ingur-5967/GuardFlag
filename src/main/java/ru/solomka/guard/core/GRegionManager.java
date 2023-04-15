package ru.solomka.guard.core;

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.entity.Player;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.utils.FileUtils;
import ru.solomka.guard.core.utils.WorldGuardHelper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class GRegionManager {

    @Getter private final String id;

    public GRegionManager(String id) {
        this.id = id;
    }

    public void createRegionFile() {
        if (getFileRegion() == null) {
            Yaml data = FileUtils.getDirectoryFile(DirectorySource.DATA.getName(), "region_" + id);
            data.set("flags", "[]");
            data.set("world", "[]");
        }
    }

    public Set<UUID> getOwners(World world) {
        return Objects.requireNonNull(WorldGuardHelper.getGuardInstance().getRegionManager(world).getRegion(id))
                .getOwners().getUniqueIds();
    }

    public Set<UUID> getUsers(World world) {
        return Objects.requireNonNull(WorldGuardHelper.getGuardInstance().getRegionManager(world).getRegion(id))
                .getMembers().getUniqueIds();
    }

    public Yaml getFileRegion() {
        return FileUtils.getDirectoryFile(DirectorySource.DATA.getName(), "region_" + id);
    }
}
