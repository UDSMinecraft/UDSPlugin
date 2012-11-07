package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.Region.RegionFlag;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;

/**
 * Provides checks for listeners.
 * @author UndeadScythes
 */
public class ListenerWrapper {
    public boolean hasProtection(Location location) {
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2()) && region.hasFlag(RegionFlag.PROTECTION)) {
                return true;
            }
        }
        return false;
    }

    public Entity getAbsoluteEntity(Entity entity) {
        if(entity instanceof Arrow) {
            return ((Arrow)entity).getShooter();
        }
        return entity;
    }

    public boolean hasFire(Location location) {
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2()) && region.hasFlag(RegionFlag.FIRE)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFood(Location location) {
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2()) && region.hasFlag(RegionFlag.FOOD)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMobs(Location location) {
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2()) && region.hasFlag(RegionFlag.MOBS)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSnow(Location location) {
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2()) && region.hasFlag(RegionFlag.SNOW)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMushrooms(Location location) {
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2()) && region.hasFlag(RegionFlag.FOOD)) {
                return true;
            }
        }
        return false;
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
