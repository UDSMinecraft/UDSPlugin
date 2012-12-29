package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Event fired a piston extends.
 * Checks that the piston involved in this event does not push a block across a
 * region boundary into another region. The correct {@link RegionFlag} will
 * allow a region to have its boundaries ignored in this particular check.
 * @author UndeadScythes
 */
public class BlockPistonExtend extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final BlockPistonExtendEvent event) {
        final List<Region> pistonRegions = regionsHere(event.getBlock().getLocation());
        final List<Block> blocks = new ArrayList<Block>(event.getBlocks());
        if(!blocks.isEmpty()) {
            blocks.add(blocks.get(blocks.size() - 1).getRelative(event.getDirection()));
        }
        for(Block block : blocks) {
            final List<Region> blockRegions = regionsHere(block.getLocation());
            if(!blockRegions.isEmpty() && crossesBoundary(pistonRegions, blockRegions)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
