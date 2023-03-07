package ru.solomka.guard.core;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.material.Redstone;
import ru.solomka.guard.core.WorldGuardHelper;

import java.util.ArrayList;
import java.util.List;

public class GRedstonePointer {

    @Getter private double xPoint,yPoint,zPoint;

    @Getter private final Location point;

    public GRedstonePointer(Location point) {
        this.point = point;
    }

    public GRedstonePointer(World world, double xPoint, double yPoint, double zPoint) {
        this(new Location(world, xPoint, yPoint, zPoint));
    }

    public List<Location> getAllPoints(ProtectedRegion currentRegion) {
        List<Location> locations = new ArrayList<>();
        for(double x = point.getX(); x < -point.getX(); x++) {
            for(double y = point.getY(); y < -point.getY(); y++) {
                for(double z = point.getZ(); z < -point.getZ(); z++) {
                    Location location = new Location(point.getWorld(), x, y, z);
                    if(location.getBlock().getType() != Material.REDSTONE || !WorldGuardHelper.isContainsInRegion(currentRegion, location)) continue;
                    locations.add(location);
                }
            }
        }
        return locations;
    }

    public boolean isRedstone() {
        return point.getBlock().getType() == Material.REDSTONE;
    }

    public boolean isEnabledRedstone() {
        if(point.getBlock().getType() != Material.REDSTONE)
            return false;

        return ((Redstone) point.getBlock()).isPowered();
    }
}
