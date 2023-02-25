package ru.solomka.guard.core.flag.module.impl;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.entity.GFlagComponent;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionEnteredEvent;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.flag.utils.GLogger;

import java.util.List;

public class FlyFlag extends GFlag<RegionEnteredEvent, FlyFlag> {

    public FlyFlag() {
        super(Flag.FLY_ENABLE.getIdFlag(), Flag.FLY_ENABLE.getArgumentsToCommand());
    }

    @Override
    public void onTrigger(RegionEnteredEvent event) {

        Player player = event.getPlayer();
        ProtectedRegion region = event.getRegion();

        GLogger.info("break");

        FlagManager flagManager = new FlagManager();

        List<GFlagComponent<?, ?>> flags = flagManager.getFlagsInRegion(region.getId());

        flags.forEach(f -> GLogger.info(f.getId()));

        player.sendMessage("Fly success enabled! Region detected");

        player.setAllowFlight(true);
        player.setFlying(true);
    }

    @Override
    public FlyFlag getInstance() {
        return this;
    }

    @Override
    public String getFailedMessage() {
        return "";
    }
}
