package ru.solomka.guard.core.flag.module.impl;

import org.bukkit.entity.Player;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionEnteredEvent;
import ru.solomka.guard.core.flag.module.GFlag;

public class FlyFlag extends GFlag<RegionEnteredEvent> {

    public FlyFlag() {
        super(Flag.FLY_ENABLE.getIdFlag(), Flag.FLY_ENABLE.getArgumentsToCommand());
    }

    @Override
    public void onTrigger(RegionEnteredEvent event) {

        Player player = event.getPlayer();

        player.sendMessage("Fly success enabled! Region detected");

        player.setAllowFlight(true);
        player.setFlying(true);
    }

    @Override
    public String getFailedMessage() {
        return "";
    }
}
