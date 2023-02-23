package ru.solomka.guard.core.flag.module.impl;

import org.bukkit.event.block.BlockExplodeEvent;
import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.module.GFlag;

public class TNTFlag extends GFlag<BlockExplodeEvent, TNTFlag> {

    public TNTFlag() {
        super(Flag.TNT_EXPLOSION.getIdFlag(), Flag.TNT_EXPLOSION.getArguments());
    }

    @Override
    public void onTrigger(BlockExplodeEvent event) {
        //todo
    }

    @Override
    public TNTFlag getInstance() {
        return this;
    }
}
