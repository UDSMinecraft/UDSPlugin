package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author UndeadScythes
 */
public class PortalUtils {
    /**
     * File name of portal file.
     */
    public static final String PATH = "portals.csv";

    private static final SaveableHashMap PORTALS = new SaveableHashMap();
    private static MatchableHashMap<Portal> MATCHABLE_PORTALS;
    
    public static void savePortals(final File path) throws IOException {
        PORTALS.save(path + File.separator + PATH);
    }
    
    public static void loadPortals(final File path) throws IOException {
        try {
            final BufferedReader file = new BufferedReader(new FileReader(path + File.separator + PATH));
            String nextLine;
            while((nextLine = file.readLine()) != null) {
                PORTALS.put(nextLine.split("\t")[0], new Portal(nextLine));
            }
            file.close();
        } catch (FileNotFoundException ex) {}
        MATCHABLE_PORTALS = PORTALS.toMatchableHashMap(Portal.class);
        for(Portal portal : MATCHABLE_PORTALS.values()) {
            portal.linkPortal();
        }
    }
    
    public static int numPortals() {
        return PORTALS.size();
    }
    
    public static Collection<Portal> getPortals() {
        return MATCHABLE_PORTALS.values();
    }
    
    public static Portal getPortal(final String name) {
        return MATCHABLE_PORTALS.get(name);
    }
    
    public static void addPortal(final Portal portal) {
        PORTALS.put(portal.getName(), portal);
        MATCHABLE_PORTALS = PORTALS.toMatchableHashMap(Portal.class);
    }
    
    public static void removePortal(final Portal portal) {
        PORTALS.remove(portal.getName());
        MATCHABLE_PORTALS = PORTALS.toMatchableHashMap(Portal.class);
    }
    
    public static boolean portalExists(final String name) {
        return PORTALS.containsKey(name);
    }
    
    private PortalUtils() {}
}