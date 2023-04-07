package ru.solomka.guard.core.flag.module.impl;

import ru.solomka.guard.core.flag.entity.enums.Flag;
import ru.solomka.guard.core.flag.event.RegionInteractBlockEvent;
import ru.solomka.guard.core.flag.module.GFlag;
import ru.solomka.guard.core.utils.WorldGuardHelper;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class InteractFlag extends GFlag<RegionInteractBlockEvent> {

    public InteractFlag() {
        super(Flag.INTERACT_ITEMS.getIdFlag(), Flag.INTERACT_ITEMS.getArgumentsToCommand());
    }

    @Override
    public void onEnable(RegionInteractBlockEvent event) {

        sendErrorMessage(event.getPlayer());

        event.setCancelled(true);
    }

    @Override
    public String getErrorMessage() {
        return translateAlternateColorCodes('&', "&6[!] &fВы не можете &cвзаимодействовать с данным блоком в чужом регионе!");
    }
}
