package ru.solomka.guard;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.solomka.guard.command.CommandManager;
import ru.solomka.guard.command.module.impl.RegionFlagCommand;
import ru.solomka.guard.config.RegistrationService;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.core.GMetaData;
import ru.solomka.guard.core.flag.event.handler.GuardEntryBlock;
import ru.solomka.guard.core.flag.module.impl.InteractFlag;
import ru.solomka.guard.core.utils.WorldGuardHelper;
import ru.solomka.guard.core.flag.FlagManager;
import ru.solomka.guard.core.flag.event.handler.GuardEntryMove;
import ru.solomka.guard.core.flag.event.handler.factory.GuardEntry;
import ru.solomka.guard.core.flag.module.impl.BuildBlockFlag;
import ru.solomka.guard.core.flag.module.impl.FlyFlag;
import ru.solomka.guard.core.gui.GUIManager;
import ru.solomka.guard.core.gui.module.impl.GuardMenu;
import ru.solomka.guard.core.gui.module.impl.controllers.ClickInventoryController;
import ru.solomka.guard.event.TriggeredRegionEvent;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        RegistrationService.registrationEvents(
                new TriggeredRegionEvent(), new GuardEntryBlock(), new GuardEntry(),
                new GuardEntryMove(), new ClickInventoryController());

        RegistrationService.initConfigs(DirectorySource.DATA, "example");
        RegistrationService.initConfigs(DirectorySource.MENU, "info_menu");
        RegistrationService.initConfigs(DirectorySource.NONE, "config");

        FlagManager.initCustomFlags(new BuildBlockFlag(), new FlyFlag(), new InteractFlag());

        GUIManager.initMenus(new GuardMenu());

        CommandManager.init(new RegionFlagCommand());

        WorldGuardHelper.checkAllRegions();
    }

    @Override
    public void onDisable() {



        GMetaData.removeContainer();
    }
}
