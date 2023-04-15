package ru.solomka.guard.core.gui.module.impl;

import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.utils.FileUtils;
import ru.solomka.guard.core.gui.module.GMenu;
import ru.solomka.guard.core.gui.module.entity.BaseElement;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuardMenu extends GMenu {

    public GuardMenu() {
        super(
                "info_menu",
                FileUtils.getDirectoryFile(DirectorySource.MENU.getName(), "info_menu").getString("option.title"),
                FileUtils.getDirectoryFile(DirectorySource.MENU.getName(), "info_menu").getInt("option.slots")
        );
    }

    @Override
    public List<BaseElement<?>> initComponents(GMenuAdapter adapter) {
        return adapter.getComponents();
    }
}