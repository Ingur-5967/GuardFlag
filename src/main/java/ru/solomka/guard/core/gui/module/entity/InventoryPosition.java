package ru.solomka.guard.core.gui.module.entity;

import lombok.Getter;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import java.util.function.Predicate;

public enum InventoryPosition {
    PLAYER_INVENTORY(i -> i instanceof PlayerInventory),
    PLAYER_CRAFT_TABLE(i -> i instanceof PlayerInventory && i instanceof CraftingInventory),
    OTHER_INVENTORY(i -> !(i instanceof PlayerInventory));

    @Getter private final Predicate<Inventory> check;

    InventoryPosition(Predicate<Inventory> check) {
        this.check = check;
    }

}
