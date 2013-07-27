package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Physics check on a block.
 * @author UndeadScythes
 */
public class BlockPhysics extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final BlockPhysicsEvent event) {
        final Block block = event.getBlock();
        if(isInQuarry(block.getLocation())) {
            event.setCancelled(true);
        }
        if(block.getType().equals(Material.PORTAL) && findPortal(block.getLocation()) != null) {
            event.setCancelled(true);
        }
    }
}
