package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Fired when a block moves from one location to another.
 * 
 * @author UndeadScythes
 */
public class BlockFromTo extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final BlockFromToEvent event) {
        final List<Region> regions = RegionUtils.getRegionsHere(event.getToBlock().getLocation());
        if(!regions.isEmpty() && !regionsContain(regions, event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }
}
