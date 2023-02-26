package ru.solomka.guard.core.gui.module.impl;

import ru.solomka.guard.core.gui.module.GMenu;
import ru.solomka.guard.core.gui.module.entity.GComponentMenu;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuardMenu extends GMenu {

    public GuardMenu() {
        super("info_menu", "Информация о регионе", 36, null);
    }

    @Override
    public List<GComponentMenu> initComponents(GMenuAdapter adapter) {
        List<GComponentMenu> componentMenuList = new ArrayList<>();

        adapter.getComponents().forEach(c -> {
            c.setTrigger(click -> click.setCancelled(true));
            componentMenuList.add(c);
        });
        return componentMenuList;
    }
}
