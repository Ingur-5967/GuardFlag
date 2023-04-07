package ru.solomka.guard.core.gui.module.impl;

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
        List<BaseElement<?>> componentMenuList = new ArrayList<>();
        adapter.getComponents().forEach(c -> {
            c.setAction(click -> click.setCancelled(true));
            componentMenuList.add(c);
        });
        return componentMenuList;
    }
}
