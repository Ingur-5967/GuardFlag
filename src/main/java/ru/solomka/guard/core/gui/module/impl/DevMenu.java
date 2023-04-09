package ru.solomka.guard.core.gui.module.impl;

import ru.solomka.guard.core.gui.module.GMenu;
import ru.solomka.guard.core.gui.module.entity.BaseElement;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;

import java.util.List;

public class DevMenu extends GMenu {

    public DevMenu() {
        super("dev_menu", "Разраб", 36);
    }

    @Override
    public List<BaseElement<?>> initComponents(GMenuAdapter adapter) {
        return adapter.getComponents();
    }
}
