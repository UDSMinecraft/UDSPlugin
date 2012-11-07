package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * When a block grows.
 * @author UndeadScythes
 */
public class BlockGrow extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(BlockGrowEvent event) {
        if(!hasMushrooms(event.getNewState().getLocation())) {
            event.setCancelled(true);
        }
    }
}
