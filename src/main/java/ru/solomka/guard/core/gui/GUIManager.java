package ru.solomka.guard.core.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.utils.FileUtils;
import ru.solomka.guard.core.gui.module.GMenu;
import ru.solomka.guard.core.gui.module.entity.BaseElement;
import ru.solomka.guard.core.gui.module.entity.GPlaceholderEntry;
import ru.solomka.guard.core.gui.tools.GPlaceholder;
import ru.solomka.guard.utils.GLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class GUIManager {

    private static final List<GMenu> MENUS_CONTAINER = new ArrayList<>();

    public static void initMenus(GMenu ...gui) {
        MENUS_CONTAINER.addAll(Arrays.stream(gui).collect(Collectors.toList()));
        GLogger.info("Successful <g>registration</res> of all GUI");
    }

    public void callGUI(@NotNull GMenu menu, Player player) {
        menu.toViewGUI(player);
    }

    public List<Integer> getActiveComponents(@NotNull GMenu gui) {
        Yaml file = FileUtils.getDirectoryFile(DirectorySource.MENU.getName(), gui.getFileControllerName());
        List<Integer> activeComponents = new ArrayList<>();
        for (int i = 0; i < gui.getSlots(); i++) {
            if(file.getString("items." + i) == null) continue;
            activeComponents.add(i);
        }
        return activeComponents;
    }

    public BaseElement<?> getComponentOfSlot(GMenu gui, int currentSlot) {
        return getComponentsGUI(gui).stream()
                .filter(m -> m.getElementOption() != null && m.getElementOption().getSlot() == currentSlot)
                .findAny().orElse(null);
    }

    public GMenu getGUIOfController(String file) {
        return MENUS_CONTAINER.stream()
                .filter(m -> m.getFileControllerName().equals(file))
                .findAny().orElse(null);
    }

    public List<BaseElement<?>> getComponentsGUI(@NotNull GMenu gui) {
        return gui.initMenu().getComponents();
    }

    public GMenu getGUIOfTitle(String title) {
        return MENUS_CONTAINER.stream().filter(m -> m.getTitle().equals(title)).findAny().orElse(null);
    }

    public static class GUIComponentBuilder {

        @Getter private final String controller;
        @Getter @Setter private ItemStack item;
        @Getter private final int id;

        private final Yaml file;

        public GUIComponentBuilder(int id, String controller) {
            this.id = id;
            this.controller = controller;
            file = FileUtils.getDirectoryFile(DirectorySource.MENU.getName(), controller);
            this.item = null;
        }

        public GUIComponentBuilder resolveItemById() {
            try {
                setItem(new ItemStack(Material.getMaterial(file.getString("items." + id + ".itemstack"))));
            } catch (NullPointerException e) {
                throw new NullPointerException("Item cannot be null!");
            }
            return this;
        }

        public GUIComponentBuilder initMeta() {

            if (item == null)
                throw new NullPointerException("GUIComponent cannot be null!");

            ItemMeta meta = item.getItemMeta();

            List<String> lore = file.getStringList("items." + id + ".lore");

            if (meta != null) {
                meta.setDisplayName(translateAlternateColorCodes('&', file.getString("items." + id + ".name")));

                GPlaceholder placeholderManager = new GPlaceholder(
                        new GPlaceholderEntry("{current_elements}", "current_elements"),
                        new GPlaceholderEntry("{valid_members}", "valid_members"),
                        new GPlaceholderEntry("{last_edit_flags}", "last_edit_flags")
                );

                lore.replaceAll(a -> translateAlternateColorCodes('&', a));
                meta.setLore(placeholderManager.replaceElements(lore));
                meta.addItemFlags(ItemFlag.values());
                item.setItemMeta(meta);
            }
            setItem(item);
            return this;
        }
    }
}