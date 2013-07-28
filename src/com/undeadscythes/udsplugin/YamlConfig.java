package com.undeadscythes.udsplugin;

import java.io.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;

/**
 *
 * @author UndeadScythes
 */
public class YamlConfig {
    private FileConfiguration config = null;
    private final File file;
    private boolean loaded = false;
    
    public YamlConfig(final String path) {
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
