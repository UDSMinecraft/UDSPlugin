package com.undeadscythes.udsplugin;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author UndeadScythes
 */
public class CustomConfig {
    private FileConfiguration config = null;
    private final File file;
    private boolean loaded = false;
    
    public CustomConfig(final String path) {
        file = new File(path);
    }
    
    public void load() {
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Bukkit.getLogger().info("Could not create config file " + file.getName() + ".");
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        loaded = true;
    }
    
    public void save() {
        if(!loaded) return;
        try {
            config.save(file);
        } catch (IOException ex) {
            Bukkit.getLogger().info("Could not save config file " + file.getName() + ".");
        }
    }
    
    public FileConfiguration get() {
        if(loaded) return config;
        return null;
    }
}
