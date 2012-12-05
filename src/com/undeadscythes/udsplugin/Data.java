package com.undeadscythes.udsplugin;

import java.io.*;
import java.util.logging.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;

/**
 * Custom configuration yaml.
 * @author UndeadScythes
 */
public class Data {
    private transient FileConfiguration dataConfig = null;
    private final transient File dataFile = new File("plugins/UDSPlugin/data.yml");;
    private final transient UDSPlugin plugin;

    private Location spawn;
    private transient long enderDeath;
    private transient long lastDaily;

    public Data(final UDSPlugin plugin) {
        this.plugin = plugin;
    }

    public void reloadData() {
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        final InputStream defaultStream = plugin.getResource("data.yml");
        if(defaultStream != null) {
            final YamlConfiguration defaultData = YamlConfiguration.loadConfiguration(defaultStream);
            dataConfig.setDefaults(defaultData);
        }
        spawn = new Location(Bukkit.getWorld(dataConfig.getString("spawn.world")), dataConfig.getDouble("spawn.x"), dataConfig.getDouble("spawn.y"), dataConfig.getDouble("spawn.z"), (float)dataConfig.getDouble("spawn.yaw"), (float)dataConfig.getDouble("spawn.pitch"));
    }

    public FileConfiguration getData() {
        if(dataConfig == null) {
            this.reloadData();
        }
        return dataConfig;
    }

    public void saveData() {
        if(dataConfig == null || dataFile == null) {
            return;
        }
        try {
            getData().save(dataFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + dataFile, ex);
        }
    }

    public void saveDefaultData() {
        if(!dataFile.exists()) {
            this.plugin.saveResource("data.yml", false);
        }
    }

    public void setSpawn(final Location location) {
        spawn = location;
        dataConfig.set("spawn.world", location.getWorld().getName());
        dataConfig.set("spawn.x", location.getX());
        dataConfig.set("spawn.y", location.getY());
        dataConfig.set("spawn.z", location.getZ());
        dataConfig.set("spawn.pitch", location.getPitch());
        dataConfig.set("spawn.yaw", location.getYaw());
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setLastEnderDeath(final long lastDeath) {
        enderDeath = lastDeath;
        dataConfig.set("ender-death", enderDeath);
    }

    public void setLastDaily() {
        lastDaily = System.currentTimeMillis();
        dataConfig.set("last-daily", lastDaily);
    }

    public long getLastEnderDeath() {
        return enderDeath;
    }

    public long getLastDaily() {
        return lastDaily;
    }
}
