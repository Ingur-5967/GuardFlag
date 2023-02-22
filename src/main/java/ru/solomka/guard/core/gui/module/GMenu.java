package ru.solomka.guard.core.gui.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.files.FileUtils;
import ru.solomka.guard.core.gui.GUIManager;
import ru.solomka.guard.core.gui.module.entity.GComponentMenu;
import ru.solomka.guard.core.gui.module.entity.GComponentOptional;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;
import ru.solomka.guard.core.gui.tools.InventoryUtils;
import ru.solomka.guard.core.gui.tools.ReplaceUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class GMenu<T extends GMenu<?>> {

    private final String title, fileControllerName;
    private final int slots;
    private final GMenu<?>[] preAndNextPages;

    public GMenu(String fileControllerName, String title, int slots, GMenu<?>[] preAndNextPages) {
        this.fileControllerName = fileControllerName;
        this.title = title;
        this.slots = slots;
        this.preAndNextPages = preAndNextPages;
    }

    public void toViewGUI(Player player) {
        player.openInventory(initMenu().getInventory());
    }

    public GMenuAdapter initMenu() {
        Inventory inventory = Bukkit.createInventory(null, getSlots(), getTitle());

        List<GComponentMenu> componentMenuList = new ArrayList<>();

        Yaml file = FileUtils.getDirectoryFile(DirectorySource.MENU.getType(), getFileControllerName());

        ItemMeta meta;

        for (int id : new GUIManager().getActiveComponents(this)) {

            ItemStack item;
            try {
                item = new ItemStack(Material.getMaterial(file.getString("Items." + id + ".ItemStack")));
            } catch (NullPointerException e) {
                throw new NullPointerException("Material '" + file.getString("Items." + id + ".ItemStack") + "' is doesnt be found");
            }

            meta = item.getItemMeta();

            if(meta != null) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', file.getString("Items." + id + ".Name")));
                meta.setLore(ReplaceUtils.getColoredList(file.getStringList("Items." + id + ".Lore")));
                meta.addItemFlags(ItemFlag.values());
                item.setItemMeta(meta);
            }

            componentMenuList.add(new GComponentMenu(
                    new GComponentOptional(
                            file.getString("Items." + id + ".Id"),
                            item, file.getInt("Items." + id + ".Slot"), meta
                    ),
                    inventory, null
            ));
            inventory.setItem(file.getInt("Items." + id + ".Slot"), item);
        }
        InventoryUtils.fillEmptySlots(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7), inventory);

        GMenuAdapter adapter = new GMenuAdapter(inventory, componentMenuList);
        adapter.setComponents(initComponents(adapter) == null ? adapter.getComponents() : initComponents(adapter));

        return adapter;
    }

    public abstract List<GComponentMenu> initComponents(GMenuAdapter adapter);

    public abstract T getInstance();
}
