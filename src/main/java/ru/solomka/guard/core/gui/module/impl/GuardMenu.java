package ru.solomka.guard.core.gui.module.impl;

import ru.solomka.guard.core.gui.module.GMenu;
import ru.solomka.guard.core.gui.module.entity.BaseElement;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuardMenu extends GMenu {

    public GuardMenu() {
        super("info_menu", "Информация о регионе", 36);
    }

    @Override
    public List<BaseElement<?>> initComponents(GMenuAdapter adapter) {
        return adapter.getComponents();
    }
}