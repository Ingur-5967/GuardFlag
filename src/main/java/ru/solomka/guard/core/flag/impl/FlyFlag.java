package ru.solomka.guard.core.flag.impl;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import ru.solomka.guard.core.flag.FlagManager;
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
        ProtectedRegion protectedRegion = event.getRegion();

        if(protectedRegion.contains((int) player.getLocation().getX(), (int) player.getLocation().getY(), (int) player.getLocation().getZ()))
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
