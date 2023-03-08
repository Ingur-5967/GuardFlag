package ru.solomka.guard.core.flag.module.impl;

import org.bukkit.entity.Player;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionEnteredEvent;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.utils.GLogger;

public class FlyFlag extends GFlag<RegionEnteredEvent> {

    public FlyFlag() {
        super(Flag.FLY_ENABLE.getIdFlag(), Flag.FLY_ENABLE.getArgumentsToCommand());
    }

    //TODO
    @Override
    public void onEnable(RegionEnteredEvent event) {
        Player player = event.getPlayer();

        if(!containsFlag(event.getRegion().getId())) return;

        player.setAllowFlight(true);
        player.setFlying(true);

        player.sendMessage("Полет включен");
    }
}
