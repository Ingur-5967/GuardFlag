package ru.solomka.guard.core.gui.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Data @AllArgsConstructor
public class GComponentOptional {
    private final String id;
    private final ItemStack viewItem;
    private final int currentSlot;
    private final ItemMeta viewItemMeta;
}
