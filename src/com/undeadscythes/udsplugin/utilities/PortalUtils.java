package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.SaveableHashMap;
import com.undeadscythes.udsplugin.Portal;
import java.io.*;
import java.util.*;

/**
 * Utility class for handling manipulation of {@link Portal} objects.
 * 
 * @author UndeadScythes
 */
public class PortalUtils {
    private static final String FILENAME = "portals.csv";
    private static final SaveableHashMap<Portal> PORTALS = new SaveableHashMap<Portal>();
    
    public static void savePortals(final File parent) throws IOException {
        PORTALS.save(parent + File.separator + FILENAME);
    }
    
    public static void loadPortals(final File parent) throws IOException {
        try {
            final BufferedReader file = new BufferedReader(new FileReader(parent + File.separator + FILENAME));
            String nextLine;
            while((nextLine = file.readLine()) != null) {
                PORTALS.put(nextLine.split("\t")[0], new Portal(nextLine));
            }
            file.close();
        } catch(FileNotFoundException ex) {}
        for(Portal portal : PORTALS.values()) {
            portal.linkPortal();
        }
    }
    
    public static int numPortals() {
        return PORTALS.size();
    }
    
    public static Collection<Portal> getPortals() {
        return PORTALS.values();
    }
    
    public static Portal getPortal(final String name) {
        return PORTALS.get(name);
    }
    
    public static void addPortal(final Portal portal) {
        PORTALS.put(portal.getName(), portal);
    }
    
    public static void removePortal(final Portal portal) {
        PORTALS.remove(portal.getName());
    }
    
    public static boolean portalExists(final String name) {
        return PORTALS.containsKey(name);
    }
    
    private PortalUtils() {}
}
