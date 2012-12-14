package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * When a piston extends.
 * @author UndeadScythes
 */
public class BlockPistonExtend extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final BlockPistonExtendEvent event) {
        final List<Block> movingBlocks = event.getBlocks();
        if(movingBlocks.isEmpty()) {
            return;
        }
        final Location pistonLocation = event.getBlock().getLocation();
        for(Block movingBlock : movingBlocks) {
            final List<Region> movingBlockRegions = regionsHere(movingBlock.getLocation());
            if(!movingBlockRegions.isEmpty()) {
                boolean mixedRegions = false;
                boolean totallyEnclosed = false;
                for(Region movingBlockRegion : movingBlockRegions) {
                    final List<Region> pistonRegions = regionsHere(pistonLocation);
                    if(pistonRegions.isEmpty()) {
                        mixedRegions = true;
                    } else {
                        for(Region pistonRegion : pistonRegions) {
                            if(movingBlockRegion.equals(pistonRegion)) {
                                totallyEnclosed = true;
                            } else {
                                mixedRegions = true;
                            }
                            if(totallyEnclosed && mixedRegions) {
                                break;
                            }
                        }
                    }
                    if(totallyEnclosed && mixedRegions) {
                        break;
                    }
                }
                if(mixedRegions && !totallyEnclosed) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
