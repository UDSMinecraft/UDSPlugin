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
    
    private static final SaveableHashMap CLANS = new SaveableHashMap();
    private static MatchableHashMap<Clan> MATCHABLE_CLANS;
    
    public static void saveClans(final File path) throws IOException {
        CLANS.save(path + File.separator + PATH);
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
        MATCHABLE_CLANS = CLANS.toMatchableHashMap(Clan.class);
    }
    
    public static int numClans() {
        return CLANS.size();
    }
    
    /**
     * Grab and cast the clans map.
     * @return Clans map.
     */
    public static Collection<Clan> getClans() {
        return MATCHABLE_CLANS.values();
    }

    public static void addClan(final Clan clan) {
        CLANS.put(clan.getName(), clan);
        MATCHABLE_CLANS = CLANS.toMatchableHashMap(Clan.class);
    }
    
    public static void removeClan(final Clan clan) {
        CLANS.remove(clan.getName());
        MATCHABLE_CLANS = CLANS.toMatchableHashMap(Clan.class);
    }
    
    public static Clan getClan(final String name) {
        return MATCHABLE_CLANS.get(name);
    }
    
    public static boolean clanExists(final String name) {
        return MATCHABLE_CLANS.containsKey(name);
    }
    
    public static List<Clan> getSortedClans(final Comparator<Clan> comp) {
        return MATCHABLE_CLANS.getSortedValues(comp);
    }
    
    private ClanUtils() {}
}
