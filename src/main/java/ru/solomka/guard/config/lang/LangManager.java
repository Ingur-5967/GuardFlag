package ru.solomka.guard.config.lang;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.solomka.guard.Main;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.utils.FileUtils;

import java.util.HashMap;
import java.util.UUID;

public class LangManager {

    public String getLang() {
        return FileUtils.getDefaultCfg("config").getString("lang");
    }

    public Yaml getFile() {
        String lang = getLang();
        return FileUtils.getDirectoryFile(DirectorySource.LANG.getName(), lang.toLowerCase() + "_" + lang.toUpperCase());
    }
}