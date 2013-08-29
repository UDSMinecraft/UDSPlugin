package com.undeadscythes.udsplugin;

import java.util.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;

/**
 * @author UndeadScythes
 */
public class Data extends YamlConfig {
    public Location spawn;
    public long enderDeath;
    public long lastDaily;
    public List<String> worlds;

    public Data(String path) {
        super(path);
        load();
        if(getConfig().getValues(false).isEmpty()) {
            World world = Bukkit.getWorld(Config.MAIN_WORLD);
            if(world != null) {
                spawn = Bukkit.getWorld(Config.MAIN_WORLD).getSpawnLocation();
            } else {
                spawn = Bukkit.getWorlds().get(0).getSpawnLocation();
            }
            enderDeath = 0;
            lastDaily = System.currentTimeMillis();
            worlds = new ArrayList<String>(0);
        } else {
            loadData();
        }
    }

    private void loadData() {
        FileConfiguration config = getConfig();
        worlds = config.getStringList("worlds");
        loadWorlds();
        spawn = new Location(Bukkit.getWorld(config.getString("spawn.world")), config.getDouble("spawn.x"), config.getDouble("spawn.y"), config.getDouble("spawn.z"), (float)config.getDouble("spawn.yaw"), (float)config.getDouble("spawn.pitch"));
        enderDeath = config.getLong("ender-death");
        lastDaily = config.getLong("last-daily");
    }

    public void reloadData() {
        loadData();
    }

    public void saveData() {
        FileConfiguration config = getConfig();
        config.set("spawn.world", spawn.getWorld().getName());
        config.set("spawn.x", spawn.getX());
        config.set("spawn.y", spawn.getY());
        config.set("spawn.z", spawn.getZ());
        config.set("spawn.pitch", spawn.getPitch());
        config.set("spawn.yaw", spawn.getYaw());
        config.set("ender-death", enderDeath);
        config.set("last-daily", lastDaily);
        config.set("worlds", worlds);
        save();
    }

    public void loadWorlds() {
        if(worlds.isEmpty()) return;
        for(String world : worlds) {
            final WorldCreator creator = new WorldCreator(world);
            creator.createWorld();
        }
    }
}
