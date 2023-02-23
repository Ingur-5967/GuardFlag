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
import ru.solomka.guard.core.flag.utils.GLogger;
import ru.solomka.guard.core.gui.GUIManager;
import ru.solomka.guard.core.gui.PlaceholderManager;
import ru.solomka.guard.core.gui.module.entity.GComponentMenu;
import ru.solomka.guard.core.gui.module.entity.GComponentOptional;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;
import ru.solomka.guard.core.gui.tools.InventoryUtils;
import ru.solomka.guard.core.gui.tools.ReplaceUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
                item = new ItemStack(Material.getMaterial(file.getString("items." + id + ".itemstack")));
            } catch (NullPointerException e) {
                throw new NullPointerException("Material '" + file.getString("items." + id + ".itemstack") + "' is doesnt be found");
            }

            meta = item.getItemMeta();


            if(meta != null) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', file.getString("items." + id + ".name")));

                PlaceholderManager<List<String>> placeholderManager = new PlaceholderManager<>(
                        new String[]{"{current_elements}", "{valid_members}", "{last_edit_flags}"},
                        new String[]{"123", "213", "321"}, file.getStringList("items." + id + ".lore")
                );

                meta.setLore(ReplaceUtils.getColoredList(placeholderManager.getReplacedElement()));
                meta.addItemFlags(ItemFlag.values());
                item.setItemMeta(meta);
            }

            componentMenuList.add(new GComponentMenu(
                    new GComponentOptional(
                            file.getString("items." + id + ".id"),
                            item, file.getInt("items." + id + ".slot"), meta
                    ),
                    inventory, null
            ));
            inventory.setItem(file.getInt("items." + id + ".slot"), item);
        }
        InventoryUtils.fillEmptySlots(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7), inventory);

        GMenuAdapter adapter = new GMenuAdapter(inventory, componentMenuList);
        adapter.setComponents(initComponents(adapter) == null ? adapter.getComponents() : initComponents(adapter));

        return adapter;
    }

    public abstract List<GComponentMenu> initComponents(GMenuAdapter adapter);

    public abstract T getInstance();
}
