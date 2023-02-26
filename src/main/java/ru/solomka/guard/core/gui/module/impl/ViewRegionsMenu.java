package ru.solomka.guard.core.gui.module.impl;

import org.bukkit.entity.Player;
import ru.solomka.guard.core.gui.module.GMenu;
import ru.solomka.guard.core.gui.module.entity.GComponentMenu;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;

import java.util.List;

public class ViewRegionsMenu extends GMenu {

    public ViewRegionsMenu() {
        super("view_regions", "Активные регионы", 36, null);
    }

    @Override
    public List<GComponentMenu> initComponents(GMenuAdapter adapter) {
        adapter.getComponents().forEach(c -> {
            c.setTrigger(t -> {
                Player player = (Player) t.getWhoClicked();
                player.sendMessage("TODO: full region information");
            });
        });
        return null;
    }
}
