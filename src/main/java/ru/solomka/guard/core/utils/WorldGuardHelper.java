package ru.solomka.guard.core.utils;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import ru.solomka.guard.Main;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WorldGuardHelper {

    public static ProtectedRegion getRegionOfContainsBlock(Block block) {
        if (block == null) return null;

        return getRegionManager(block.getWorld()).getApplicableRegions(block.getLocation()).getRegions()
                .stream().filter(r -> r.contains(block.getX(), block.getY(), block.getZ())).findAny().orElse(null);
    }

    public static ProtectedRegion getRegionOfContainsLocation(Location loc) {
        return getRegionManager(loc.getWorld()).getApplicableRegions(loc).getRegions()
                .stream().filter(r -> r.contains((int) loc.getX(), (int) loc.getY(), (int) loc.getZ())).findAny().orElse(null);
    }

    public static Location getCenterRegionLocation(ProtectedRegion region) {

        for (World w : Bukkit.getWorlds()) {
            RegionManager rg = getRegionManager(w);
            if (rg.getRegion(region.getId()) == null) continue;

            Location top = new Location(w, 0, 0, 0);
            top.setX(region.getMaximumPoint().getX());
            top.setY(region.getMaximumPoint().getY());
            top.setZ(region.getMaximumPoint().getZ());

            Location bottom = new Location(w, 0, 0, 0);
            bottom.setX(region.getMinimumPoint().getX());
            bottom.setY(region.getMinimumPoint().getY());
            bottom.setZ(region.getMinimumPoint().getZ());

            double X = ((bottom.getX() - top.getX()) / 2) + bottom.getX();
            double Y = ((bottom.getY() - top.getY()) / 2) + bottom.getY();
            double Z = ((bottom.getZ() - top.getZ()) / 2) + bottom.getZ();

            return new Location(w, X, Y, Z);
        }
        return null;
    }

    public static void checkAllRegions() {
        File dir = new File(Main.getInstance().getDataFolder() + File.separator + DirectorySource.DATA.getName());

        if (!dir.exists() || !dir.isDirectory() || dir.listFiles() == null) return;
        if (dir.listFiles() == null) return;

        List<File> files = Arrays.stream(Objects.requireNonNull(dir.listFiles())).collect(Collectors.toList());

        for (File f : files) {

            if (!f.getName().contains("_")) return;

            String regionName = f.getName().split("_")[1].split("\\.")[0];
            Yaml file = FileUtils.getDirectoryFile(DirectorySource.DATA.getName(), f.getName().split("\\.yml")[0]);

            if (file.getString("world") == null || Bukkit.getWorld(file.getString("world")) == null)
                f.delete();
            else {
                String nameWorld = FileUtils.getDirectoryFile(DirectorySource.DATA.getName(), f.getName()).getString("world");
                RegionManager regionManager = WorldGuardHelper.getRegionManager(Bukkit.getWorld(nameWorld));

                if (regionManager.getRegion(regionName) == null)
                    f.delete();
            }
        }
    }

    public static WorldGuardPlugin getGuardInstance() {
        Plugin plugin = Main.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null) {
            Main.getInstance().onDisable();
            throw new NullPointerException("WorldGuard plugin cannot be null!");
        }
        return (WorldGuardPlugin) plugin;
    }

    public static boolean isContainsInRegion(ProtectedRegion region, Location location) {
        return region.contains(location.getBlock().getX(), location.getBlock().getY(), location.getBlock().getZ());
    }

    public static RegionManager getRegionManager(World world) {
        return WGBukkit.getRegionManager(world);
    }
}