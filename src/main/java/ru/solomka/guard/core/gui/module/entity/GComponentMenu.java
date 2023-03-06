package ru.solomka.guard.core.gui.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

@Data @AllArgsConstructor
public class GComponentMenu {
    private final String id;
    private final GComponentOptional optional;
    private final Inventory parent;
    @Setter private Consumer<InventoryClickEvent> trigger;
}
