package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * Threaded class to run scheduled functions for maintenance.
 * @author UndeadScythes
 */
public class QuarryRefill implements Runnable {
    /**
     * Initiates the timer.
     * @param plugin The UDSPlugin.
     * @param interval The interval between passes.
     */
    public QuarryRefill() {}

    /**
     * The function that will be used on each schedule.
     */
    @Override
    public void run() {
        for(Region quarry : UDSPlugin.getRegions(RegionType.QUARRY).values()) {
            final Material material = Material.getMaterial(quarry.getData().toUpperCase());
            final int dX = quarry.getV2().getBlockX() - quarry.getV1().getBlockX();
            final int dY = quarry.getV2().getBlockY() - quarry.getV1().getBlockY();
            final int dZ = quarry.getV2().getBlockZ() - quarry.getV1().getBlockZ();
            for(int x = 0; x <= dX; x++) {
                for(int y = 0; y <= dY; y++) {
                    for(int z = 0; z <= dZ; z++) {
                        quarry.getWorld().getBlockAt(x, y, z).setType(material);
                    }
                }
            }
        }
        UDSPlugin.sendBroadcast("The quarries have been refilled.");
    }
}
