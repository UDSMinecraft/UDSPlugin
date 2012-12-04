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
        final List<Block> blocks = event.getBlocks();
        if(blocks.isEmpty()) {
            return;
        }
        final Location piston = event.getBlock().getLocation();
        for(Block i : blocks) {
            final List<Region> testRegions1 = regionsHere(i.getLocation());
            if(!testRegions1.isEmpty()) {
                boolean mixedRegions = false;
                boolean totallyEnclosed = false;
                for(Region j : testRegions1) {
                    final List<Region> testRegions2 = regionsHere(piston);
                    if(testRegions2.isEmpty()) {
                        mixedRegions = true;
                    } else {
                        for(Region k : testRegions2) {
                            if(!j.equals(k)) {
                                mixedRegions = true;
                            }
                            if(j.equals(k)) {
                                totallyEnclosed = true;
                            }
                        }
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
