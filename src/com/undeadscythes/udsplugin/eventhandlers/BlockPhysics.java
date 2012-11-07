package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Physics check on a block.
 * @author UndeadScythes
 */
public class BlockPhysics extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(BlockPhysicsEvent event) {
        if(isInQuarry(event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }
}
