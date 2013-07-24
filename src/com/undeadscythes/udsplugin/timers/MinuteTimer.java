package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.*;

/**
 * Threaded class to run scheduled functions for maintenance.
 * @author UndeadScythes
 */
public class MinuteTimer implements Runnable {
    private enum Time {
        DAY,
        NIGHT;
        
        public static Time getTime(final long time) {
            if(time < 1000 || time > 11000) {
                return NIGHT;
            }
            return DAY;
        }
    }
    
    private HashMap<World, Time> worldTimes = new HashMap<World, Time>();
    
    /**
     * Initiates the timer.
     */
    public MinuteTimer() {}
    
    /**
     * The function that will be used on each schedule.
     */
    @Override
    public void run() {
        for(final World world : Bukkit.getWorlds()) {
            final Time time = Time.getTime(world.getTime());
            if(worldTimes.containsKey(world) && worldTimes.get(world).equals(time)) {
                continue;
            }
            for(final Region region : UDSPlugin.getRegions(RegionType.GENERIC).values()) {
                if(!region.getWorld().equals(world) || !region.hasFlag(RegionFlag.LAMP)) continue;
                switch(time) {
                    case NIGHT:
                        region.replace(Material.REDSTONE_LAMP_OFF, Material.REDSTONE_LAMP_ON);
                        break;
                    case DAY:
                        region.replace(Material.REDSTONE_LAMP_ON, Material.REDSTONE_LAMP_OFF);
                        break;
                }
            }
            worldTimes.put(world, time);
        }
    }
}
