package ru.solomka.guard;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.solomka.guard.command.CommandManager;
import ru.solomka.guard.command.impl.RegionFlagCommand;
import ru.solomka.guard.config.RegistrationService;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.core.event.BlockTriggeredEvent;
import ru.solomka.guard.core.gui.ControllerManager;
import ru.solomka.guard.core.gui.GUIManager;
import ru.solomka.guard.core.gui.impl.GuardMenu;
import ru.solomka.guard.core.gui.impl.controllers.ClickInventoryController;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        CommandManager.init(new RegionFlagCommand());
        RegistrationService.registrationEvents(new BlockTriggeredEvent());
        RegistrationService.initConfigs(DirectorySource.DATA, "example");
        RegistrationService.initConfigs(DirectorySource.MENU, "info_menu");

        new ControllerManager().initControllers(new ClickInventoryController());

        GUIManager.initMenus(new GuardMenu());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
