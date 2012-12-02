package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Description.
 * @author UndeadScythes
 */
public class BlockPistonExtend extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(BlockPistonExtendEvent event) {
        List<Block> blocks = event.getBlocks();
        if(blocks.isEmpty()) {
            return;
        }
        Location piston = event.getBlock().getLocation();
        for(Iterator<Block> i = blocks.iterator(); i.hasNext();) {
            List<Region> testRegions1 = regionsHere(i.next().getLocation());
            if(!testRegions1.isEmpty()) {
                boolean mixedRegions = false;
                boolean totallyEnclosed = false;
                for(Region j : testRegions1) {
                    List<Region> testRegions2 = regionsHere(piston);
                    if(!testRegions2.isEmpty()) {
                        for(Region k : testRegions2) {
                            if(!j.equals(k)) {
                                mixedRegions = true;
                            }
                            if(j.equals(k)) {
                                totallyEnclosed = true;
                            }
                        }
                    }
                    else {
                        mixedRegions = true;
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
