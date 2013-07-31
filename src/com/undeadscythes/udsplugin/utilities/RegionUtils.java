package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author UndeadScythes
 */
public class RegionUtils {
    private static final String PATH = "regions.csv";
    private static HashMap<RegionType, SaveableHashMap<Region>> REGIONS = new HashMap<RegionType, SaveableHashMap<Region>>();

    public static void saveRegions(final File path) throws IOException {
        final SaveableHashMap<Region> regions = new SaveableHashMap<Region>();
        for(final SaveableHashMap<Region> map : REGIONS.values()) {
            for(final Region region : map.values()) {
                regions.put(region.getName(), region);
            }
        }
        regions.save(path + File.separator + PATH);
    }
    
    public static void loadRegions(final File path) throws IOException {
        for(final RegionType type : RegionType.values()) {
            REGIONS.put(type, new SaveableHashMap<Region>());
        }
        try {
            final BufferedReader file = new BufferedReader(new FileReader(path + File.separator + PATH));
            String nextLine;
            while((nextLine = file.readLine()) != null) {
                final Region region = new Region(nextLine);
                REGIONS.get(region.getType()).put(region.getName(), region);
            }
            file.close();
        } catch (FileNotFoundException ex) {}
    }
    
    public static int numRegions() {
        int size = 0;
        for(final SaveableHashMap<Region> map : REGIONS.values()) {
            size += map.size();
        }
        return size;
    }
    
    /**
     * Grab the regions list of the corresponding type.
     * @param type Region type.
     * @return The corresponding list.
     */
    public static Collection<Region> getRegions(final RegionType type) {
        if(type == null) {
            return null;
        }
        return REGIONS.get(type).values();
    }

    public static Region getRegion(final RegionType type, final String name) {
        if(type == null) {
            return null;
        }
        return REGIONS.get(type).get(name);
    }
    
    public static Region getRegion(final String name) {
        for(final SaveableHashMap<Region> map : REGIONS.values()) {
            if(map.containsKey(name)) {
                return map.get(name);
            }
        }
        return null;
    }
    
    public static Collection<Region> getRegions() {
        final ArrayList<Region> regions = new ArrayList<Region>();
        for(final SaveableHashMap<Region> map : REGIONS.values()) {
            for(final Region region : map.values()) {
                regions.add(region);
            }
        }
        return regions;
    }
    
    public static void renameRegion(final Region region, final String name) {
        REGIONS.get(region.getType()).remove(region.getName());
        region.rename(name);
        REGIONS.get(region.getType()).put(name, region);
    }
    
    public static void addRegion(final Region region) {
        REGIONS.get(region.getType()).put(region.getName(), region);
    }
    
    public static void removeRegion(final Region region) {
        REGIONS.get(region.getType()).remove(region.getName());
    }
    
    
    public static void removeRegion(final RegionType type, final String name) {
        REGIONS.get(type).remove(name);
    }
    
    public static Region matchRegion(final RegionType type, final String partial) {
        final Region region = REGIONS.get(type).get(partial);
        if(region != null) {
            return region;
        }
        return REGIONS.get(type).matchKey(partial);
    }
    
    public static boolean regionExists(final RegionType type, final String name) {
        return REGIONS.get(type).containsKey(name);
    }
    
    public static boolean regionExists(final String name) {
        return getRegion(name) != null;
    }
    
    public static List<Region> getSortedRegions(final RegionType type, final Comparator<Region> comp) {
        return REGIONS.get(type).getSortedValues(comp);
    }
    
    public static void changeType(final Region region, final RegionType type) {
        REGIONS.get(region.getType()).remove(region.getName());
        region.setType(type);
        REGIONS.get(type).put(region.getName(), region);
    }
    
    private RegionUtils() {}
}
