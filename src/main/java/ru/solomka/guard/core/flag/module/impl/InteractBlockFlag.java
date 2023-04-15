package ru.solomka.guard.core.flag.module.impl;

import ru.solomka.guard.core.flag.entity.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionInteractBlockEvent;
import ru.solomka.guard.core.flag.module.GFlag;

public class InteractBlockFlag extends GFlag<RegionInteractBlockEvent> {

    public InteractBlockFlag(String idFlag, Object[] allowParams) {
        super(Flag.INTERACT_BLOCKS.getIdFlag(), Flag.INTERACT_BLOCKS.getArgumentsToCommand());
    }

    @Override
    public void onEnable(RegionInteractBlockEvent event) {
    }
}
