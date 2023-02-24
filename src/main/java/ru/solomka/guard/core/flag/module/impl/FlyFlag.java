package ru.solomka.guard.core.flag.module.impl;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import ru.solomka.guard.core.WorldGuardHelper;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionEnteredEvent;
import ru.solomka.guard.core.flag.event.RegionMovingEvent;
import ru.solomka.guard.core.flag.module.GFlag;

public class FlyFlag extends GFlag<RegionEnteredEvent, FlyFlag> {

    public FlyFlag() {
        super(Flag.FLY_ENABLE.getIdFlag(), Flag.FLY_ENABLE.getArguments());
    }

    @Override
    public void onTrigger(RegionEnteredEvent event) {

        Player player = event.getPlayer();
        ProtectedRegion region = event.getRegion();

        player.sendMessage("Fly success enabled! Region detected");

        player.setAllowFlight(true);
        player.setFlying(true);
    }

    @Override
    public FlyFlag getInstance() {
        return this;
    }
}
