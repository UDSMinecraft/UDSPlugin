package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.Region.RegionFlag;
import java.util.*;
import org.bukkit.*;

/**
 * Provides checks for listeners.
 * @author UndeadScythes
 */
public class ListenerWrapper {
    public boolean hasFire(Location location) {
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2()) && !region.hasFlag(RegionFlag.FIRE)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasMobs(Location location) {
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2()) && !region.hasFlag(RegionFlag.MOBS)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasSnow(Location location) {
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2()) && !region.hasFlag(RegionFlag.SNOW)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasMushrooms(Location location) {
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2()) && !region.hasFlag(RegionFlag.FOOD)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Region> regionsHere(Location location) {
        ArrayList<Region> regions = new ArrayList<Region>();
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2())) {
                regions.add(region);
            }
        }
        return regions;
    }

    public boolean regionsContain(ArrayList<Region> regions, Location location) {
        for(Region region : regions) {
            if(location.toVector().isInAABB(region.getV1(), region.getV2())) {
                return true;
            }
        }
        return false;
    }

    public boolean isInQuarry(Location location) {
        for(Region quarry : UDSPlugin.getQuarries().values()) {
            if(location.toVector().isInAABB(quarry.getV1(), quarry.getV2())) {
                return true;
            }
        }
        return false;
    }
}
