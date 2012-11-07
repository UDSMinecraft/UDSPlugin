package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * A block melts.
 * @author UndeadScythes
 */
public class BlockFade extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(BlockFadeEvent event) {
        if(!hasSnow(event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }
}
