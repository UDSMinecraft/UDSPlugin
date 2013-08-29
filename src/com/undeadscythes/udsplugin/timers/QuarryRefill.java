package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.block.*;

/**
 * Scheduled task to refill public quarries each day.
 * 
 * @author UndeadScythes
 */
public class QuarryRefill implements Runnable {
    public QuarryRefill() {}

    @Override
    public final void run() {
        for(Region quarry : RegionUtils.getRegions(RegionType.QUARRY)) {
            final Material material = Material.getMaterial(quarry.getData().toUpperCase());
            for(int x = quarry.getV1().getBlockX(); x <= quarry.getV2().getBlockX(); x++) {
                for(int y = quarry.getV1().getBlockY(); y <= quarry.getV2().getBlockY(); y++) {
                    for(int z = quarry.getV1().getBlockZ(); z <= quarry.getV2().getBlockZ(); z++) {
                        final Block block = quarry.getWorld().getBlockAt(x, y, z);
                        if(!block.getType().equals(Material.BEDROCK)) {
                            block.setType(material);
                        }
                    }
                }
            }
        }
        UDSPlugin.sendBroadcast("The quarries have been refilled.");
    }
}
