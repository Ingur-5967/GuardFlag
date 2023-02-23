package ru.solomka.guard.core.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.files.FileUtils;
import ru.solomka.guard.core.gui.module.GMenu;
import ru.solomka.guard.core.gui.module.entity.GComponentMenu;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GUIManager {

    private static final List<GMenu<?>> MENUS_CONTAINER = new ArrayList<>();

    public static void initMenus(GMenu<?> ...gui) {
        MENUS_CONTAINER.addAll(Arrays.stream(gui).collect(Collectors.toList()));



        System.out.printf("Successfully registered '%s' interfaces", gui.length);
    }

    public void callGUI(@NotNull GMenu<?> menu, Player player) {
        menu.toViewGUI(player);
    }

    public List<Integer> getActiveComponents(@NotNull GMenu<?> gui) {
        Yaml file = FileUtils.getDirectoryFile(DirectorySource.MENU.getType(), gui.getFileControllerName());
        List<Integer> activeComponents = new ArrayList<>();
        for (int i = 0; i < gui.getSlots(); i++) {
            if(file.getString("items." + i) == null) continue;
            activeComponents.add(i);
        }
        return activeComponents;
    }

    public GComponentMenu getComponentOfSlot(GMenu<?> gui, int currentSlot) {
        return getComponentsGUI(gui).stream().filter(m -> m.getOptional().getCurrentSlot() == currentSlot).findAny().orElse(null);
    }

    public List<GComponentMenu> getComponentsGUI(@NotNull GMenu<?> gui) {
        return gui.initMenu().getComponents();
    }

    public GMenu<?> getGUIOfTitle(String title) {
        return MENUS_CONTAINER.stream().filter(m -> m.getTitle().equals(title)).findAny().orElse(null);
    }
}
