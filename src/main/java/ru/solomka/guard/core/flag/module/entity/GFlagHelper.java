package ru.solomka.guard.core.flag.module.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GFlagHelper<E extends Event> implements Listener {
    public void onTrigger(E event) {}
    public void onTrigger(Player player, E event) {}
}
