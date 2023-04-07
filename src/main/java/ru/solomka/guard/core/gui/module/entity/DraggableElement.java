package ru.solomka.guard.core.gui.module.entity;

import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface DraggableElement {

    void onDrag(InventoryDragEvent e);

    InventoryPosition getValidPosition();

    default void cancelAction(Inventory from, ItemStack item, InventoryDragEvent e) {
        int oldSlot = -1;
        for (int i = 0; i < from.getSize(); i++) {
            if(from.getItem(i) != item) continue;
            oldSlot = i;
            break;
        }
        from.setItem(oldSlot, item);
        e.setCursor(null);
    }
}
