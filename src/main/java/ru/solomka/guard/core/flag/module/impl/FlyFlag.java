package ru.solomka.guard.core.flag.module.impl;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionMovingEvent;
import ru.solomka.guard.core.flag.module.GFlag;

public class FlyFlag extends GFlag<RegionMovingEvent, FlyFlag> {

    public FlyFlag() {
        super(Flag.FLY_ENABLE.getIdFlag(), Flag.FLY_ENABLE.getArguments());
    }

    @Override
    public void onTrigger(RegionMovingEvent event) {

        Player player = event.getPlayer();
        ProtectedRegion region = event.getRegion();

        if(region.contains(player.getLocation().getBlock().getX(), player.getLocation().getBlock().getY(), player.getLocation().getBlock().getZ()))
            return;

        player.sendMessage("test");

        player.setAllowFlight(true);
        player.setFlying(true);

    }

    @Override
    public FlyFlag getInstance() {
        return this;
    }
}
