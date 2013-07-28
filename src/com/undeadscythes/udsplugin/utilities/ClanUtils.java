package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.commands.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author UndeadScythes
 */
public class ClanUtils {
    /**
     * File name of clan file.
     */
    public static final String PATH = "clans.csv";
    
    private static MatchableHashMap<Clan> CLANS;
    
    public static void saveClans(final File path) throws IOException {
        final MatchableHashMap<Clan> clans = new MatchableHashMap<Clan>();
        for(final Clan clan : CLANS.values()) {
            clans.put(clan.getName(), clan);
        }
        clans.save(path + File.separator + PATH);
    }
    
    public static void loadClans(final File path) throws IOException {
        try {
            final BufferedReader file = new BufferedReader(new FileReader(path + File.separator + PATH));
            String nextLine;
            while((nextLine = file.readLine()) != null) {
                final Clan clan = new Clan(nextLine);
                CLANS.put(nextLine.split("\t")[0], clan);
                clan.linkMembers();
            }
            file.close();
        } catch (FileNotFoundException ex) {}
    }
    
    public static int numClans() {
        return CLANS.size();
    }
    
    /**
     * Grab and cast the clans map.
     * @return Clans map.
     */
    public static Collection<Clan> getClans() {
        return CLANS.values();
    }

    public static void addClan(final Clan clan) {
        CLANS.put(clan.getName(), clan);
    }
    
    public static void removeClan(final Clan clan) {
        CLANS.remove(clan.getName());
    }
    
    public static Clan getClan(final String name) {
        return CLANS.get(name);
    }
    
    public static boolean clanExists(final String name) {
        return CLANS.containsKey(name);
    }
    
    public static List<Clan> getSortedClans(final Comparator<Clan> comp) {
        return CLANS.getSortedValues(comp);
    }
    
    private ClanUtils() {}
}
