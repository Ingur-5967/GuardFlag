package ru.solomka.guard.core.gui.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.solomka.guard.Main;

import java.util.function.Consumer;

public abstract class BaseElement<T extends BaseElement<T>> implements Listener {

    @Getter private final String className;
    @Getter private final int id;
    @Getter private final ElementOption elementOption;
    @Getter @Setter private Consumer<InventoryClickEvent> action;

    public BaseElement(String className, int id, ElementOption elementOption) {
        this.className = className;
        this.id = id;
        this.elementOption = elementOption;
        this.action = a -> {};
    }

    public abstract void init(GMenuAdapter adapter);

    public abstract T getInstance();

    public BaseElement<T> registerComponent() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
        return this;
    }

    @Data @AllArgsConstructor
    public static class ElementOption {
        private final ItemStack item;
        private final int slot;
    }
}