package ru.solomka.guard.core.flag.module.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

public class GFlagHelper<E extends Event> extends GFlagAdapter {
    public void onTrigger(E event) {}
    public void onTrigger(Player player,E event) {}
}
