package ru.solomka.guard.core.gui.impl;

import org.bukkit.Bukkit;
import ru.solomka.guard.core.gui.module.GMenu;
import ru.solomka.guard.core.gui.module.entity.GComponentMenu;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuardMenu extends GMenu<GuardMenu> {

    public GuardMenu() {
        super("info_menu", "Информация о регионе", 36, null);
    }

    @Override
    public List<GComponentMenu> initComponents(GMenuAdapter adapter) {
        List<GComponentMenu> componentMenuList = new ArrayList<>();

        adapter.getComponents().forEach(c -> {
            c.setTrigger(click -> {
                click.getWhoClicked().closeInventory();
                click.getWhoClicked().sendMessage("Все будет потом)");
            });
            componentMenuList.add(c);
        });
        return componentMenuList;
    }

    @Override
    public GuardMenu getInstance() {
        return this;
    }
}
