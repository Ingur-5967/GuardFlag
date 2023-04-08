package ru.solomka.guard.core.gui.module.entity.component;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import ru.solomka.guard.core.gui.module.entity.BaseElement;
import ru.solomka.guard.core.gui.module.entity.DraggableElement;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;
import ru.solomka.guard.core.gui.module.entity.InventoryPosition;
import ru.solomka.guard.utils.GLogger;

public class GSlider extends BaseElement<GSlider> implements DraggableElement {

    protected GSlider(int id, ElementOption elementOption) {
        super("SLIDER", id, elementOption);
    }

    @Override
    public void init(GMenuAdapter adapter) {
        //todo
    }

    @Override
    public GSlider getInstance() {
        return null;
    }

    @Override
    public void onDrag(InventoryDragEvent e) {
        if(!getValidPosition().getCheck().test(e.getInventory())) {
            cancelAction(e.getInventory(), e.getCursor(), e);
            return;
        }
        GLogger.info("ok");
    }

    @Override
    public InventoryPosition getValidPosition() {
        return InventoryPosition.OTHER_INVENTORY;
    }
}
