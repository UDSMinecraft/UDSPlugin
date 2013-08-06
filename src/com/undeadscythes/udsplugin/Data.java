package com.undeadscythes.udsplugin;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;

/**
 * Custom configuration yaml for settings designed to be changed in-game.
 * 
 * @author UndeadScythes
 */
public class Data {
    private FileConfiguration dataConfig = null;
    private final File dataFile = new File("plugins/UDSPlugin/data.yml");;
    private final UDSPlugin plugin;
    private Location spawn;
    private long enderDeath;
    private long lastDaily;
    private List<String> worlds;

    public Data(final UDSPlugin plugin) {
        this.plugin = plugin;
    }

    public final void reloadData() {
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        final InputStream defaultStream = plugin.getResource("data.yml");
        if(defaultStream != null) {
            final YamlConfiguration defaultData = YamlConfiguration.loadConfiguration(defaultStream);
            dataConfig.setDefaults(defaultData);
        }
        spawn = new Location(Bukkit.getWorld(dataConfig.getString("spawn.world")), dataConfig.getDouble("spawn.x"), dataConfig.getDouble("spawn.y"), dataConfig.getDouble("spawn.z"), (float)dataConfig.getDouble("spawn.yaw"), (float)dataConfig.getDouble("spawn.pitch"));
        enderDeath = dataConfig.getLong("ender-death");
        lastDaily = dataConfig.getLong("last-daily");
        worlds = dataConfig.getStringList("worlds");
    }

    public final FileConfiguration getData() {
        if(dataConfig == null) {
            this.reloadData();
        }
        return dataConfig;
    }

    public final void saveData() {
        if(dataConfig == null || dataFile == null) {
            return;
        }
        try {
            getData().save(dataFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + dataFile, ex);
        }
    }

    public final void saveDefaultData() {
        if(!dataFile.exists()) {
            this.plugin.saveResource("data.yml", false);
        }
    }

    public final void setSpawn(final Location location) {
        spawn = location;
        dataConfig.set("spawn.world", location.getWorld().getName());
        dataConfig.set("spawn.x", location.getX());
        dataConfig.set("spawn.y", location.getY());
        dataConfig.set("spawn.z", location.getZ());
        dataConfig.set("spawn.pitch", location.getPitch());
        dataConfig.set("spawn.yaw", location.getYaw());
    }

    public final Location getSpawn() {
        return spawn;
    }

    public final void setLastEnderDeath(final long lastDeath) {
        enderDeath = lastDeath;
        dataConfig.set("ender-death", enderDeath);
    }

    public final void setLastDaily() {
        lastDaily = System.currentTimeMillis();
        dataConfig.set("last-daily", lastDaily);
    }

    public final long getLastEnderDeath() {
        return enderDeath;
    }

    public final long getLastDaily() {
        return lastDaily;
    }

    public final List<String> getWorlds() {
        return worlds; //TODO: Fix me!
    }

    public final void newWorld(final String world) {
        worlds.add(world);
        dataConfig.set("worlds", worlds);
    }
}
