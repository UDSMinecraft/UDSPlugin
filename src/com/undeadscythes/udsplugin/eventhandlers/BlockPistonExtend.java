package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import java.util.*;
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
        final List<Region> pistonRegions = regionsHere(event.getBlock().getLocation());
        final List<Block> movingBlocks = new ArrayList<Block>();
        movingBlocks.addAll(event.getBlocks());
        if(!movingBlocks.isEmpty()) {
            movingBlocks.add(movingBlocks.get(movingBlocks.size() - 1).getRelative(event.getDirection()));
        }
        for(Block movingBlock : movingBlocks) {
            final List<Region> movingBlockRegions = regionsHere(movingBlock.getLocation());
            if(!movingBlockRegions.isEmpty() && crossesBoundary(pistonRegions, movingBlockRegions)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
