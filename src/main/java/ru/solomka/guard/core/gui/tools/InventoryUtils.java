package ru.solomka.guard.core.gui.tools;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InventoryUtils {

    public static void fillEmptySlots(ItemStack currentItem, @NotNull Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++)
            if (inventory.getItem(i) == null) inventory.setItem(i, currentItem);
    }

    public static boolean compareMaterials(Material first, Material second) {
        return toItemStack(first).equals(toItemStack(second));
    }

    public static ItemStack toItemStack(Material value) {
        return new ItemStack(value);
    }
}