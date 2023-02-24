package ru.solomka.guard.core;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
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

    public static @NotNull WorldGuardPlugin getGuardInstance() {
        Plugin plugin = Main.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        if(plugin == null) {
            throw new NullPointerException("WorldGuard plugin cannot be null!");
        }
        return (WorldGuardPlugin) plugin;
    }

    public static ProtectedRegion getRegionOfContainsBlock(Block block) {
        if(block == null) return null;
        for(Map.Entry<String, ProtectedRegion> aMap : getRegionManager(block.getWorld()).getRegions().entrySet()) {
            if(isContainsInRegion(aMap.getValue(), block.getLocation())) return aMap.getValue();
        }
        return null;
    }

    public static boolean isContainsInRegion(ProtectedRegion region, Location location) {
        return region.contains(location.getBlock().getX(), location.getBlock().getY(), location.getBlock().getZ());
    }

    public static RegionManager getRegionManager(World world) {
        return WGBukkit.getRegionManager(world);
    }
}