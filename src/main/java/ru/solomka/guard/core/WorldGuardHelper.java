package ru.solomka.guard.core;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.solomka.guard.Main;
import ru.solomka.guard.core.flag.utils.GLogger;

import java.util.Map;
import java.util.Objects;

public class WorldGuardHelper {

    public static ProtectedRegion getRegionOfContainsBlock(Block block) {
        if (block == null) return null;
        for (Map.Entry<String, ProtectedRegion> aMap : getRegionManager(block.getWorld()).getRegions().entrySet()) {
            if (isContainsInRegion(aMap.getValue(), block.getLocation())) return aMap.getValue();
        }
        return null;
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