package ru.solomka.guard.core.gui.module;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import ru.solomka.guard.config.RegistrationService;

public abstract class GController<C> implements Listener {

    public abstract C getInstance();

    public void register() {
        RegistrationService.registrationEvents(this);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }
}
