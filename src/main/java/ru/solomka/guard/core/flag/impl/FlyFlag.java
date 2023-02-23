package ru.solomka.guard.core.flag.impl;

import ru.solomka.guard.core.flag.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionEnteringEvent;
import ru.solomka.guard.core.flag.module.GFlag;

public class FlyFlag extends GFlag<RegionEnteringEvent, FlyFlag> {

    public FlyFlag() {
        super(Flag.FLY_ENABLE.getIdFlag(), Flag.FLY_ENABLE.getArguments());
    }

    @Override
    public void onTrigger(RegionEnteringEvent event) {

    }

    @Override
    public FlyFlag getInstance() {
        return this;
    }
}
