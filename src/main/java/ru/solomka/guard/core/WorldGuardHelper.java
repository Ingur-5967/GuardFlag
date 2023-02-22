package ru.solomka.guard.core;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.Main;

public class WorldGuardHelper {

    public static @NotNull WorldGuardPlugin getGuardInstance() {
        Plugin plugin = Main.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        if(plugin == null) {
            throw new NullPointerException("WorldGuard plugin cannot be null!");
        }
        return (WorldGuardPlugin) plugin;
    }

    public static RegionManager getRegionManager(World world) {
        return WGBukkit.getRegionManager(world);
    }
}