package ru.solomka.guard.core.flag.module.impl;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import ru.solomka.guard.core.GRedstonePointer;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionRedstoneEvent;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.utils.GLogger;

import java.util.List;

public class RedstoneFlag extends GFlag<RegionRedstoneEvent> {

    public RedstoneFlag() {
        super(Flag.REDSTONE_CONTROLLER.getIdFlag(), Flag.REDSTONE_CONTROLLER.getArgumentsToCommand());
    }

    @Override
    public void onEnable(RegionRedstoneEvent event) {

        Player player = event.getExecutor();
        ProtectedRegion region = event.getCurrentRegion();
        Block block = event.getTriggeredBlock();

        GRedstonePointer redstonePointer = new GRedstonePointer(block.getLocation());

        if(redstonePointer.isRedstone()) {
            GLogger.info("test");
        }

    }
}
