package ru.solomka.guard.core.gui.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.bukkit.inventory.Inventory;

import java.util.List;

@Data @AllArgsConstructor
public class GMenuAdapter {
    private final Inventory inventory;
    @Setter private List<BaseElement<?>> components;
}
