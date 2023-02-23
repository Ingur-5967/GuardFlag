package ru.solomka.guard.core.flag.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.module.GFlag;

public class BuildBlockFlag extends GFlag<BlockEvent, BuildBlockFlag> {

    public BuildBlockFlag() {
        super(Flag.BLOCK_BUILD.getIdFlag(), Flag.BLOCK_BUILD.getArguments());
    }

    @Override
    public void onTrigger(Player player, BlockEvent event) {
        player.sendMessage("123");
    }

    @Override
    public BuildBlockFlag getInstance() {
        return this;
    }
}
