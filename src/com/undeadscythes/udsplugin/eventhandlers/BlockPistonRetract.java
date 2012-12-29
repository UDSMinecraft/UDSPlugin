package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * When a piston retracts.
 * @author UndeadScythes
 */
public class BlockPistonRetract extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final BlockPistonRetractEvent event) {
        if(event.isSticky()) {
            final List<Region> blockRegions = regionsHere(event.getRetractLocation());
            if(!blockRegions.isEmpty()) {
                final List<Region> pistonRegions = regionsHere(event.getBlock().getLocation());
                if(pistonRegions.isEmpty() || crossesBoundary(pistonRegions, blockRegions)) {
                    switch(event.getDirection()) {
                        case DOWN:
                            event.getBlock().setTypeIdAndData(Material.PISTON_STICKY_BASE.getId(), (byte) 0, true);
                            break;
                        case UP:
                            event.getBlock().setTypeIdAndData(Material.PISTON_STICKY_BASE.getId(), (byte) 1, true);
                            break;
                        case WEST:
                            event.getBlock().setTypeIdAndData(Material.PISTON_STICKY_BASE.getId(), (byte) 4, true);
                            break;
                        case EAST:
                            event.getBlock().setTypeIdAndData(Material.PISTON_STICKY_BASE.getId(), (byte) 5, true);
                            break;
                        case SOUTH:
                            event.getBlock().setTypeIdAndData(Material.PISTON_STICKY_BASE.getId(), (byte) 3, true);
                            break;
                        case NORTH:
                            event.getBlock().setTypeIdAndData(Material.PISTON_STICKY_BASE.getId(), (byte) 2, true);
                            break;
                    }
                    event.getBlock().getRelative(event.getDirection()).setTypeId(0);
                    event.setCancelled(true);
                }
            }
        }
    }
}
