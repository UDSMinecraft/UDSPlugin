package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.*;
import java.io.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

/**
 * Utility class for handling manipulations with {@link SaveablePlayer} objects.
 *
 * @author UndeadScythes
 */
public class PlayerUtils {
    private static final String FILENAME = "players.csv";
    private static final SaveableHashMap<SaveablePlayer> PLAYERS = new SaveableHashMap<SaveablePlayer>();
    private static final SaveableHashMap<SaveablePlayer> HIDDEN_PLAYERS = new SaveableHashMap<SaveablePlayer>();
    private static final SaveableHashMap<SaveablePlayer> ONLINE_PLAYERS = new SaveableHashMap<SaveablePlayer>();
    private static final SaveableHashMap<SaveablePlayer> VIPS = new SaveableHashMap<SaveablePlayer>();
    private static final HashMap<World, YamlConfig> INVENTORIES = new HashMap<World, YamlConfig>(3);

    public static Collection<SaveablePlayer> getPlayers() {
        return PLAYERS.values();
    }

    public static SaveablePlayer getPlayer(final String name) {
        return PLAYERS.get(name);
    }

    public static List<SaveablePlayer> getSortedPlayers(final Comparator<SaveablePlayer> comparator) {
        return PLAYERS.getSortedValues(comparator);
    }

    public static SaveablePlayer matchPlayer(final String partial) {
        return PLAYERS.matchKey(partial);
    }

    public static void addPlayer(final SaveablePlayer player) {
        PLAYERS.put(player.getName(), player);
    }

    public static boolean playerExists(final String name) {
        return PLAYERS.containsKey(name);
    }

    public static int numPlayers() {
        return PLAYERS.size();
    }

    public static Collection<SaveablePlayer> getVips() {
        return VIPS.values();
    }

    public static Collection<SaveablePlayer> getHiddenPlayers() {
        return HIDDEN_PLAYERS.values();
    }

    public static void addHiddenPlayer(final SaveablePlayer player) {
        HIDDEN_PLAYERS.put(player.getName(), player);
    }

    public static void removeHiddenPlayer(final String name) {
        HIDDEN_PLAYERS.remove(name);
    }

    public static Collection<SaveablePlayer> getOnlinePlayers() {
        return ONLINE_PLAYERS.values();
    }

    public static SaveablePlayer getOnlinePlayer(final String name) {
        return ONLINE_PLAYERS.get(name);
    }

    public static SaveablePlayer getOnlinePlayer(final Player player) {
        return ONLINE_PLAYERS.get(player.getName());
    }

    public static SaveablePlayer matchOnlinePlayer(final String partial) {
        return ONLINE_PLAYERS.matchKey(partial);
    }

    public static void addOnlinePlayer(final SaveablePlayer player) {
        ONLINE_PLAYERS.put(player.getName(), player);
    }

    public static SaveablePlayer removeOnlinePlayer(final String name) {
        return ONLINE_PLAYERS.remove(name);
    }

    public static void savePlayers(final File parent) throws IOException {
        PLAYERS.save(parent + File.separator + FILENAME);
        for(final YamlConfig config : INVENTORIES.values()) {
            config.save();
        }
    }

    public static void loadPlayers(final File parent) throws IOException {
        try {
            final BufferedReader file = new BufferedReader(new FileReader(parent + File.separator + FILENAME));
            String nextLine;
            while((nextLine = file.readLine()) != null) {
                final SaveablePlayer player = new SaveablePlayer(nextLine);
                PLAYERS.put(nextLine.split("\t")[0], player);
                if(player.getVIPTime() > 0) {
                    VIPS.put(player.getName(), player);
                }
            }
            file.close();
        } catch (FileNotFoundException ex) {}
        for(final World world : Bukkit.getWorlds()) {
            INVENTORIES.put(world, new YamlConfig(UDSPlugin.DATA_PATH + "/inventories/" + world.getName() + ".yml"));
            INVENTORIES.get(world).load();
        }
    }

    public static void saveInventory(final SaveablePlayer player, final World world) {
        final FileConfiguration config = INVENTORIES.get(world).getConfig();
        config.set(player.getName() + ".inventory", player.getInventory().getContents());
        config.set(player.getName() + ".armor", player.getInventory().getArmorContents());
    }

    public static void loadInventory(final SaveablePlayer player, final World world) {
        final FileConfiguration config = INVENTORIES.get(world).getConfig();
        if(config.contains(player.getName() + ".inventory") && config.contains(player.getName() + ".armor")) {
            player.getInventory().setContents(config.getList(player.getName() + ".inventory").toArray(new ItemStack[36]));
            player.getInventory().setArmorContents(config.getList(player.getName() + ".armor").toArray(new ItemStack[4]));
        } else {
            player.getInventory().clear(-1, -1);
        }
    }

    public static void saveInventory(final SaveablePlayer player) {
        saveInventory(player, player.getWorld());
    }

    public static void loadInventory(final SaveablePlayer player) {
        loadInventory(player, player.getWorld());
    }

    public static int numActivePlayers() {
        int count = 0;
        for(final SaveablePlayer player : PLAYERS.values()) {
            count += player.isActive() ? 1 : 0;
        }
        return count;
    }

    private PlayerUtils() {}
}
