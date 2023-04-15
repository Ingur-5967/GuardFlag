package ru.solomka.guard.core.gui;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.utils.FileUtils;
import ru.solomka.guard.core.gui.module.GMenu;
import ru.solomka.guard.utils.GLogger;

import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.*;

public class GUIController {

    @Getter private final String fileName;

    @Getter private final Yaml file;

    private static final GUIManager GUI_MANAGER = new GUIManager();

    private static final GCommandGUI<?, ?>[] SUPPORTED_COMMANDS = new GCommandGUI[]{
            new GCommandGUI<Object, Player>("CLOSE", false, "",  (obj, player) -> player.closeInventory()),

            new GCommandGUI<String, Player>("OPEN", true, ":", (controllerName, player) -> {
                if(controllerName.equals("")) return;
                GMenu gui = GUI_MANAGER.getGUIOfController(controllerName);
                if(gui == null) return;
                gui.toViewGUI(player);
            }),

            new GCommandGUI<String, Player>("SEND", true, ":", (message, player) -> player.sendMessage(translateAlternateColorCodes('&', message))),

            new GCommandGUI<Integer, Player>("NEXT_PAGE", true, ":", (idPage, player) -> {}), //todo

            new GCommandGUI<Integer, Player>("PREV_PAGE", true, ":", (idPage, player) -> {}), //todo
    };

    public GUIController(String fileName) {
        this.fileName = fileName;
        this.file = FileUtils.getDirectoryFile(DirectorySource.MENU.getName(), fileName);
    }

    @SuppressWarnings("unchecked")
    public <T, C> void execute(String command, T object, C entity) {
        GCommandGUI<T, C> gCommand = (GCommandGUI<T, C>) findByName(command);
        if(command == null || (!gCommand.getSplitter().equals("") && Objects.equals(entity, new Object())))
            return;

        gCommand.getAction().accept(object, entity);
    }

    public List<GCommandGUI<?, ?>> getGCommands(int idItem) {

        List<String> commands = getListCommands(idItem);

        List<GCommandGUI<?, ?>> gCommands = new LinkedList<>();

        for(String command : commands) {
            String element = command.contains(":") ? command.split(":")[0].toUpperCase() : command;
            if(findByName(element) == null) continue;
            gCommands.add(findByName(element));
        }
        return gCommands;
    }

    public GCommandGUI<?, ?> findByName(String command) {
        return Arrays.stream(SUPPORTED_COMMANDS)
                .filter(s -> s.getCommand().equals(command.toUpperCase()))
                .findAny().orElse(null);
    }

    private List<String> getListCommands(int idItem) {
        return file.getStringList("items." + idItem + ".action");
    }

    @Data @AllArgsConstructor
    public static class GCommandGUI<T, C> {
        private final String command;
        private final boolean needArgument;
        private final String splitter;
        private final BiConsumer<T, C> action;
    }
}