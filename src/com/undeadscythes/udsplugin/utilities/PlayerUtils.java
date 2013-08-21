package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import java.io.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

/**
 * @author UndeadScythes
 */
public class PlayerUtils {
    private static final SortableHashMap<Member> MEMBERS = new SortableHashMap<Member>();
    private static final SortableHashMap<Member> HIDDEN_PLAYERS = new SortableHashMap<Member>();
    private static final SortableHashMap<Member> ONLINE_PLAYERS = new SortableHashMap<Member>();
    private static final SortableHashMap<Member> VIPS = new SortableHashMap<Member>();
    private static final HashMap<World, YamlConfig> INVENTORIES = new HashMap<World, YamlConfig>(3);

    public static Collection<Member> getPlayers() {
        return MEMBERS.values();
    }

    public static Member getPlayer(final String name) {
        return MEMBERS.get(name);
    }

    public static List<Member> getSortedPlayers(final Comparator<Member> comparator) {
        return MEMBERS.getSortedValues(comparator);
    }

    public static Member matchPlayer(final String partial) {
        return MEMBERS.matchKey(partial);
    }

    public static void addPlayer(final Member player) {
        MEMBERS.put(player.getName(), player);
    }

    public static boolean playerExists(final String name) {
        return MEMBERS.containsKey(name);
    }

    public static int numPlayers() {
        return MEMBERS.size();
    }

    public static Collection<Member> getVips() {
        return VIPS.values();
    }

    public static Collection<Member> getHiddenPlayers() {
        return HIDDEN_PLAYERS.values();
    }

    public static void addHiddenPlayer(final Member player) {
        HIDDEN_PLAYERS.put(player.getName(), player);
    }

    public static void removeHiddenPlayer(final String name) {
        HIDDEN_PLAYERS.remove(name);
    }

    public static Collection<Member> getOnlinePlayers() {
        return ONLINE_PLAYERS.values();
    }

    public static Member getOnlinePlayer(final String name) throws PlayerNotOnlineException {
        if(ONLINE_PLAYERS.containsKey(name)) {
            return ONLINE_PLAYERS.get(name);
        }
        throw new PlayerNotOnlineException(name);
    }

    public static Member getOnlinePlayer(final Member member) throws PlayerNotOnlineException {
        if(ONLINE_PLAYERS.containsKey(member.getName())) {
            return ONLINE_PLAYERS.get(member.getName());
        }
        throw new PlayerNotOnlineException(member.getName());
    }

    public static Member getOnlinePlayer(final Player player) {
        return ONLINE_PLAYERS.get(player.getName());
    }

    public static Member matchOnlinePlayer(final String partial) {
        return ONLINE_PLAYERS.matchKey(partial);
    }

    public static void addOnlinePlayer(final Member player) {
        ONLINE_PLAYERS.put(player.getName(), player);
    }

    public static Member removeOnlinePlayer(final String name) {
        return ONLINE_PLAYERS.remove(name);
    }

    public static void savePlayers() throws IOException {
        MetaCore.save();
        for(final YamlConfig config : INVENTORIES.values()) {
            config.save();
        }
    }

    public static void loadPlayers(final File parent) throws IOException {
        try {
            final BufferedReader file = new BufferedReader(new FileReader(parent + File.separator + "players.csv"));
            String nextLine;
            while((nextLine = file.readLine()) != null) {
                String playerName = nextLine.split("\t")[0];
                MEMBERS.put(playerName, new Member(nextLine, UDSPlugin.SERVER.getOfflinePlayer(playerName)));
            }
            file.close();
        } catch (FileNotFoundException ex) {
            for(OfflinePlayer player : UDSPlugin.SERVER.getOfflinePlayers()) {
                MEMBERS.put(player.getName(), new Member(player));
            }
        }
        for(final World world : Bukkit.getWorlds()) {
            INVENTORIES.put(world, new YamlConfig(UDSPlugin.DATA_PATH + "/inventories/" + world.getName() + ".yml"));
            INVENTORIES.get(world).load();
        }
    }

    public static void saveInventory(final Member player, final World world) {
        final FileConfiguration config = INVENTORIES.get(world).getConfig();
        config.set(player.getName() + ".inventory", player.getInventory().getContents());
        config.set(player.getName() + ".armor", player.getInventory().getArmorContents());
    }

    @SuppressWarnings("unchecked")
    public static void loadInventory(final Member player, final World world) {
        final FileConfiguration config = INVENTORIES.get(world).getConfig();
        if(config.contains(player.getName())) {
            Object obj = config.get(player.getName() + ".inventory");
            if(obj instanceof ArrayList) {
                player.getInventory().setContents(((List<ItemStack>)obj).toArray(new ItemStack[36]));
                player.getInventory().setArmorContents(((List<ItemStack>)config.getList(player.getName() + ".armor")).toArray(new ItemStack[4]));
            } else {
                player.getInventory().setContents((ItemStack[])obj);
                player.getInventory().setArmorContents((ItemStack[])config.get(player.getName() + ".armor"));
            }
        } else {
            player.getInventory().clear(-1, -1);
        }
    }

    public static void saveInventory(final Member player) {
        saveInventory(player, player.getWorld());
    }

    public static void loadInventory(final Member player) {
        loadInventory(player, player.getWorld());
    }

    public static int numActivePlayers() {
        int count = 0;
        for(final Member player : MEMBERS.values()) {
            count += player.isActive() ? 1 : 0;
        }
        return count;
    }

    private PlayerUtils() {}
}
