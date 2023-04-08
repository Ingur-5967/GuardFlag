package ru.solomka.guard.core.gui.module;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.utils.FileUtils;
import ru.solomka.guard.core.gui.GUIController;
import ru.solomka.guard.core.gui.GUIManager;
import ru.solomka.guard.core.gui.module.entity.BaseElement;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;
import ru.solomka.guard.core.gui.module.entity.component.GButton;
import ru.solomka.guard.core.gui.tools.InventoryUtils;
import ru.solomka.guard.utils.GLogger;

import java.util.ArrayList;
import java.util.List;

public abstract class GMenu {

    @Getter private final String title, fileControllerName;
    @Getter private final int slots;

    public GMenu(String fileControllerName, String title, int slots) {
        this.fileControllerName = fileControllerName;
        this.title = title;
        this.slots = slots;
    }

    public void toViewGUI(Player player) {
        player.openInventory(initMenu().getInventory());
    }

    public GMenuAdapter initMenu() {

        Inventory inventory = Bukkit.createInventory(null, getSlots(), getTitle());
        List<BaseElement<?>> componentMenuList = new ArrayList<>();

        Yaml file = FileUtils.getDirectoryFile(DirectorySource.MENU.getType(), getFileControllerName());

        for (int id : new GUIManager().getActiveComponents(this)) {

            ItemStack item = new GUIManager.GUIComponentBuilder(id, fileControllerName).resolveItemById().initMeta().getItem();

            GButton gButton = new GButton(id, new BaseElement.ElementOption(item, file.getInt("items." + id + ".slot")))
                    .registerComponent().getInstance();

            GUIController guiController = new GUIController(getFileControllerName());
            List<GUIController.GCommandGUI<?, ?>> commands = guiController.getGCommands(id);
            List<String> fCommands = file.getStringList("items." + id + ".action");

            GLogger.info(commands.size());

            gButton.setAction(s -> {
                int index = 0;

                s.setCancelled(true);

                if(fCommands.isEmpty())
                    return;

                for(GUIController.GCommandGUI<?, ?> command : commands) {
                    guiController.execute(
                            command.getCommand(),
                            !command.isNeedArgument() ? new Object() : fCommands.get(index).split(":")[1],
                            (Player) s.getWhoClicked()
                    );
                    index++;
                }
            });
            componentMenuList.add(gButton);
            inventory.setItem(file.getInt("items." + id + ".slot"), item);
        }

        InventoryUtils.fillEmptySlots(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7), inventory);
        GMenuAdapter adapter = new GMenuAdapter(inventory, componentMenuList);

        componentMenuList.forEach(e -> e.init(adapter));

        adapter.setComponents(initComponents(adapter) == null ? adapter.getComponents() : initComponents(adapter));

        return adapter;
    }

    public abstract List<BaseElement<?>> initComponents(GMenuAdapter adapter);
}
