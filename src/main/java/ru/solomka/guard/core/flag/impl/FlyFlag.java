package ru.solomka.guard.core.flag.impl;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionEnteringEvent;
import ru.solomka.guard.core.flag.module.GFlag;

public class FlyFlag extends GFlag<RegionEnteringEvent, FlyFlag> {

    public FlyFlag() {
        super(Flag.FLY_ENABLE.getIdFlag(), Flag.FLY_ENABLE.getArguments());
    }

    @Override
    public void onTrigger(RegionEnteringEvent event) {

        Player player = event.getPlayer();
        ProtectedRegion protectedRegion = event.getRegion();

        FlagManager flagManager = new FlagManager();

        //if(flagManager.getFlagsInRegion(protectedRegion.getId()) == null) return;

        player.sendMessage("test");

        player.setAllowFlight(true);
        player.setFlying(true);

    }

    @Override
    public FlyFlag getInstance() {
        return this;
    }
}
