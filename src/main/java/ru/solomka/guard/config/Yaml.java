package ru.solomka.guard.config;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.Main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

public class Yaml {

    @Getter private YamlConfiguration yaml = new YamlConfiguration();
    private File file;

    public Yaml(@NotNull File file) {
        this.file = file;
        if (file.exists()) try {
            load();
        } catch (IOException | InvalidConfigurationException e) {
            yaml = null;
            e.printStackTrace();
        }
    }

    public Yaml(String path, String name, boolean mode) {
        if (!new File(Main.getInstance().getDataFolder() + "/" + path, name).exists()) {
            File dir = new File(Main.getInstance().getDataFolder() + "/" + path + "/");
            if (!dir.exists())
                dir.mkdir();
            File file = new File(dir, name);
            YamlConfiguration cfg = loadConfiguration(file);
            cfg.options().copyDefaults(mode);
            Main.getInstance().saveResource(path + "/" + name, false);
        }
    }

    public Yaml(String name) {
        if (!new File(Main.getInstance().getDataFolder(), name).exists()) {
            File file = new File(Main.getInstance().getDataFolder(), name);
            YamlConfiguration cfg = loadConfiguration(file);
            cfg.options().copyDefaults(true);
            Main.getInstance().saveResource(name, false);
        }
    }

    public void load() throws IOException, InvalidConfigurationException {
        yaml.load(file);
    }

    public void save() throws IOException {
        yaml.save(file);
    }

    public void reload() {
        try {
            save();
            load();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public <T> void set(String path, T value) {
        yaml.set(path, String.valueOf(value));
        reload();
    }

    public int getInt(String path) {
        long request = this.getLong(path);
        return request <= Integer.MIN_VALUE ? Integer.MIN_VALUE : request >= Integer.MAX_VALUE ? Integer.MAX_VALUE : Long.valueOf(request).shortValue();
    }

    public short getShort(String path) {
        long request = this.getLong(path);
        return request <= Short.MIN_VALUE ? Short.MIN_VALUE : request >= Short.MAX_VALUE ? Short.MAX_VALUE : Long.valueOf(request).shortValue();
    }

    public long getLong(String path) {
        return yaml.getLong(path);
    }

    public double getDouble(String path) {
        return yaml.getDouble(path);
    }

    public float getFloat(String path) {
        double request = this.getDouble(path);
        return request <= Float.MIN_VALUE ? Float.MIN_VALUE : request >= Float.MAX_VALUE ? Float.MAX_VALUE : Double.valueOf(request).floatValue();
    }

    public String getString(String path) {
        return yaml.getString(path);
    }

    public Object getObject(String path) {
        return yaml.get(path);
    }

    public boolean getBoolean(String path) {
        return yaml.getBoolean(path);
    }

    public int getInt(String path, int def, boolean restore) throws IOException {
        if (restore) if (this.getString(path) == null) set(path, def);
        return this.getInt(path);
    }

    public long getLong(String path, long def, boolean restore) throws IOException {
        if (restore) if (this.getString(path) == null) set(path, def);
        return this.getLong(path);
    }

    public double getDouble(String path, double def, boolean restore) throws IOException {
        if (restore) if (this.getString(path) == null) set(path, def);
        return this.getDouble(path);
    }

    public String getString(String path, String def, boolean restore) throws IOException {
        if (restore) if (this.getString(path) == null) set(path, def);
        return this.getString(path);
    }

    public boolean getBoolean(String path, boolean def, boolean restore) throws IOException {
        if (restore) if (this.getString(path) == null) set(path, def);
        return yaml.getBoolean(path);
    }

    public boolean isExistDirectory(String directory) {
        File path = new File(Main.getInstance().getDataFolder() + File.separator + directory);
        if (path.exists()) return true;
        return false;
    }

    public List<Long> getLongList(String path) {
        return yaml.getLongList(path);
    }

    public List<Integer> getIntList(String path) {
        return yaml.getIntegerList(path);
    }

    public List<Short> getShortList(String path) {
        return yaml.getShortList(path);
    }

    public List<Double> getDoubleList(String path) {
        return yaml.getDoubleList(path);
    }

    public List<Float> getFloatList(String path) {
        return yaml.getFloatList(path);
    }

    public List<String> getStringList(String path) {
        return yaml.getStringList(path);
    }
}