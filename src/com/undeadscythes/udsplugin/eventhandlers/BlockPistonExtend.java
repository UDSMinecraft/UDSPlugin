package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.util.Vector;

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
        if(!event.isSticky() && event.getDirection().equals(BlockFace.UP) && event.getLength() == 1) {
            switch(event.getBlocks().get(0).getType()) {
                case SAND:
                    boost(event.getBlocks().get(0), Material.SAND);
                    break;
                case GRAVEL:
                    boost(event.getBlocks().get(0), Material.GRAVEL);
                    break;
                default:
            }
        }
    }

    private void boost(final Block block, final Material material) {
        block.setType(Material.AIR);
        final FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation().add(0.5, 1.5, 0.5), material, (byte)0);
        fallingBlock.setVelocity(new Vector(0, Config.PISTON_POWER / 10, 0));
    }
}
