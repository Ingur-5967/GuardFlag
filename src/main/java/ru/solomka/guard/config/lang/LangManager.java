package ru.solomka.guard.config.lang;

import lombok.Getter;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.config.enums.DirectorySource;
import ru.solomka.guard.config.utils.FileUtils;

public class LangManager {

    public String getLang() {
        return FileUtils.getDefaultCfg("config").getString("lang");
    }

    public Yaml getFile() {
        String lang = getLang();
        return FileUtils.getDirectoryFile(DirectorySource.LANG.getName(), lang.toLowerCase() + "_" + lang.toUpperCase());
    }
}