package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.SaveableHashMap;
import com.undeadscythes.udsplugin.Warp;
import java.io.*;
import java.util.*;

/**
 * Utility class for handling manipulation of {@link Warp} objects.
 * 
 * @author UndeadScythes
 */
public class WarpUtils {
    private static final String FILENAME = "warps.csv";
    private static final SaveableHashMap<Warp> WARPS = new SaveableHashMap<Warp>();

    public static int numWarps() {
        return WARPS.size();
    }
    
    public static void saveWarps(final File parent) throws IOException {
        WARPS.save(parent + File.separator + FILENAME);
    }
    
    public static void loadWarps(final File parent) throws IOException {
        try {
            final BufferedReader file = new BufferedReader(new FileReader(parent + File.separator + FILENAME));
            String nextLine;
            while((nextLine = file.readLine()) != null) {
                WARPS.put(nextLine.split("\t")[0], new Warp(nextLine));
            }
            file.close();
        } catch(FileNotFoundException ex) {}
    }
    
    public static Collection<Warp> getWarps() {
        return WARPS.values();
    }
    
    public static Warp getWarp(final String name) {
        return WARPS.get(name);
    }
    
    public static Warp matchWarp(final String partial) {
        return WARPS.matchKey(partial);
    }
    
    public static void addWarp(final Warp warp) {
        WARPS.put(warp.getName(), warp);
    }
    
    public static void removeWarp(final Warp warp) {
        WARPS.remove(warp.getName());
    }
    
    private WarpUtils() {}
}
