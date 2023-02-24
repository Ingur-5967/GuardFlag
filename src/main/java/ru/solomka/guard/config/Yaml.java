package ru.solomka.guard.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.Main;
import ru.solomka.guard.core.flag.utils.GLogger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

public class Yaml {

    private YamlConfiguration yaml = new YamlConfiguration();
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
            if(!dir.exists())
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

    public FileConfiguration getFileConfiguration() {
        return yaml;
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

    @SafeVarargs // FIXME: 24.02.2023 
    public final <T> void set(String path, boolean addToList, T... value) {
        if(addToList) {
            if (yaml.getString(path) == null) yaml.set(path, "");

            if(value.length > 2)
                yaml.getStringList(path).addAll(Arrays.as)

            GLogger.info("break");

            if(value[0] instanceof Collection<?>)
                yaml.getStringList(path).addAll((Collection<? extends String>) value[0]);

            else {


            }
            for (T o : value) {

                if(o instanceof Collection<?>) {
                    GLogger.info("TO COLLECTION");
                    yaml.getStringList(path).addAll((Collection<? extends String>) o);
                } else {
                    GLogger.info("TO String");



                    yaml.getStringList(path).add(String.valueOf(o));
                }
            }
        }

        else yaml.set(path, value[0]);

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

    public int getInt(String path, int def, boolean restore) {
        if (restore) if (this.getString(path) == null) set(path, false, def);
        return this.getInt(path);
    }

    public long getLong(String path, long def, boolean restore) {
        if (restore) if (this.getString(path) == null) set(path, false, def);
        return this.getLong(path);
    }

    public double getDouble(String path, double def, boolean restore) {
        if (restore) if (this.getString(path) == null) set(path, false, def);
        return this.getDouble(path);
    }

    public String getString(String path, String def, boolean restore) {
        if (restore) if (this.getString(path) == null) set(path, false, def);
        return this.getString(path);
    }

    public boolean getBoolean(String path, boolean def, boolean restore) {
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