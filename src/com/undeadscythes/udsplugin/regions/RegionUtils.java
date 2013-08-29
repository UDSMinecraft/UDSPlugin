package com.undeadscythes.udsplugin.regions;

import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsplugin.SaveableHashMap;
import com.undeadscythes.udsplugin.regions.*;
import java.io.*;
import java.util.*;
import org.bukkit.*;

/**
 * Utility class for handling manipulation of {@link Region} objects.
 * 
 * @author UndeadScythes
 */
public class RegionUtils {
    private static final String FILENAME = "regions.csv";
    private static final EnumMap<RegionType, SaveableHashMap<Region>> REGIONS = new EnumMap<RegionType, SaveableHashMap<Region>>(RegionType.class);

    public static void saveRegions(final File parent) throws IOException {
        final SaveableHashMap<Region> regions = new SaveableHashMap<Region>();
        for(final SaveableHashMap<Region> map : REGIONS.values()) {
            for(final Region region : map.values()) {
                regions.put(region.getName(), region);
            }
        }
        regions.save(parent + File.separator + FILENAME);
    }
    
    public static void loadRegions(final File parent) throws IOException {
        for(final RegionType type : RegionType.values()) {
            REGIONS.put(type, new SaveableHashMap<Region>());
        }
        try {
            final BufferedReader file = new BufferedReader(new FileReader(parent + File.separator + FILENAME));
            String nextLine;
            while((nextLine = file.readLine()) != null) {
                final Region region = new Region(nextLine);
                REGIONS.get(region.getType()).put(region.getName(), region);
            }
            file.close();
        } catch(FileNotFoundException ex) {}
    }
    
    public static int numRegions() {
        int size = 0;
        for(final SaveableHashMap<Region> map : REGIONS.values()) {
            size += map.size();
        }
        return size;
    }
    
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
        final ArrayList<Region> regions = new ArrayList<Region>(0);
        for(final SaveableHashMap<Region> map : REGIONS.values()) {
            for(final Region region : map.values()) {
                regions.add(region);
            }
        }
        return regions;
    }
    
    public static void renameRegion(final Region region, final String newName) {
        REGIONS.get(region.getType()).remove(region.getName());
        region.rename(newName);
        REGIONS.get(region.getType()).put(newName, region);
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
    
    public static List<Region> getSortedRegions(final RegionType type, final Comparator<Region> comparator) {
        return REGIONS.get(type).getSortedValues(comparator);
    }
    
    public static void changeType(final Region region, final RegionType newType) {
        REGIONS.get(region.getType()).remove(region.getName());
        region.setType(newType);
        REGIONS.get(newType).put(region.getName(), region);
    }
    
    public static List<Region> getRegionsHere(final Location location) {
        final List<Region> regions = new ArrayList<Region>(0);
        final org.bukkit.util.Vector vector = location.toVector();
        for(Region region : RegionUtils.getRegions()) {
            if(location.getWorld().equals(region.getWorld()) && vector.isInAABB(region.getV1(), region.getV2())) {
                regions.add(region);
            }
        }
        return regions;
    }


    
    private RegionUtils() {}
}
