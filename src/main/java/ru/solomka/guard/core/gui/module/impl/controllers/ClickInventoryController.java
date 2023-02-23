package ru.solomka.guard.core.gui.module.impl.controllers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.solomka.guard.core.gui.GUIManager;
import ru.solomka.guard.core.gui.module.GController;
import ru.solomka.guard.core.gui.module.entity.GComponentMenu;

public class ClickInventoryController extends GController<ClickInventoryController> {

    @EventHandler
    public void onEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        int slot = event.getSlot();

        GUIManager guiManager = new GUIManager();

        if(inventory == null || inventory == player.getInventory()) return;

        event.setCancelled(true);

        GComponentMenu component = guiManager.getComponentOfSlot(guiManager.getGUIOfTitle(inventory.getTitle()), slot);

        if(component == null || component.getTrigger() == null) return;

        component.getTrigger().accept(event);
    }

    @Override
    public ClickInventoryController getInstance() {
        return this;
    }
}