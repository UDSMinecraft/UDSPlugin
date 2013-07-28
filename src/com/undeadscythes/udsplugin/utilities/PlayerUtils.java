package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author UndeadScythes
 */
public class PlayerUtils {
    /**
     * File name of player file.
     */
    private static final String PATH = "players.csv";
    private static SaveableHashMap<SaveablePlayer> PLAYERS;
    private static final SaveableHashMap<SaveablePlayer> HIDDEN_PLAYERS = new SaveableHashMap<SaveablePlayer>();
    private static final SaveableHashMap<SaveablePlayer> ONLINE_PLAYERS = new SaveableHashMap<SaveablePlayer>();
    private static final SaveableHashMap<SaveablePlayer> VIPS = new SaveableHashMap<SaveablePlayer>();

    /**
     * Grab and cast the players map.
     * @return Players map.
     */
    public static Collection<SaveablePlayer> getPlayers() {
        return PLAYERS.values();
    }
    
    public static SaveablePlayer getPlayer(final String name) {
        return PLAYERS.get(name);
    }
    
    public static List<SaveablePlayer> getSortedPlayers(final Comparator<SaveablePlayer> comp) {
        return PLAYERS.getSortedValues(comp);
    }
    
    public static SaveablePlayer matchPlayer(final String partial) {
        return PLAYERS.matchKey(partial);
    }
    
    public static void addPlayer(final SaveablePlayer player) {
        PLAYERS.put(player.getName(), player);
    }
    
    public static boolean existingPlayer(final String name) {
        return PLAYERS.containsKey(name);
    }
    
    public static int numPlayers() {
        return PLAYERS.size();
    }

    /**
     * Grab the VIPs map.
     * @return VIPs map.
     */
    public static Collection<SaveablePlayer> getVips() {
        return VIPS.values();
    }

    /**
     * Grab the hidden players map.
     * @return Hidden players map.
     */
    public static Collection<SaveablePlayer> getHiddenPlayers() {
        return HIDDEN_PLAYERS.values();
    }
    
    public static void addHiddenPlayer(final SaveablePlayer player) {
        HIDDEN_PLAYERS.put(player.getName(), player);
    }
    
    public static void removeHiddenPlayer(final String name) {
        HIDDEN_PLAYERS.remove(name);
    }

    /**
     * Grab the online players map.
     * @return Online players map.
     */
    public static Collection<SaveablePlayer> getOnlinePlayers() {
        return ONLINE_PLAYERS.values();
    }
    
    public static SaveablePlayer getOnlinePlayer(final String name) {
        return ONLINE_PLAYERS.get(name);
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
    
    public static void savePlayers(final File path) throws IOException {
        PLAYERS.save(path + File.separator + PATH);
    }
    
    public static void loadPlayers(final File path) throws IOException {
        try {
            final BufferedReader file = new BufferedReader(new FileReader(path + File.separator + PATH));
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
    }
    
    private PlayerUtils() {}
}
