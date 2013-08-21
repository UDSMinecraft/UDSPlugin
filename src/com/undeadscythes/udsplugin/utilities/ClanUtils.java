package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.SaveableHashMap;
import com.undeadscythes.udsplugin.Clan;
import java.io.*;
import java.util.*;

/**
 * Utility class for handling manipulations with {@link Clan} objects.
 * 
 * @author UndeadScythes
 */
public class ClanUtils {
    private static final String FILENAME = "clans.csv";
    private static final SaveableHashMap<Clan> CLANS = new SaveableHashMap<Clan>();
    
    public static void saveClans(final File parent) throws IOException {
        CLANS.save(parent + File.separator + FILENAME);
    }
    
    public static void loadClans(final File parent) throws IOException {
        try {
            final BufferedReader file = new BufferedReader(new FileReader(parent + File.separator + FILENAME));
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
    
    public static List<Clan> getSortedClans(final Comparator<Clan> comparator) {
        return CLANS.getSortedValues(comparator);
    }
    
    private ClanUtils() {}
}
