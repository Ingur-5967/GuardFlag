package ru.solomka.guard.core.gui.module.impl;

import org.bukkit.entity.Player;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.utils.FileUtils;
import ru.solomka.guard.core.gui.GUIController;
import ru.solomka.guard.core.gui.module.GMenu;
import ru.solomka.guard.core.gui.module.entity.BaseElement;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;
import ru.solomka.guard.utils.GLogger;

import java.util.ArrayList;
import java.util.List;

public class GuardMenu extends GMenu {

    public GuardMenu() {
        super("info_menu", "Информация о регионе", 36);
    }

    @Override
    public List<BaseElement<?>> initComponents(GMenuAdapter adapter) {
        return new ArrayList<>(adapter.getComponents());
    }
}
