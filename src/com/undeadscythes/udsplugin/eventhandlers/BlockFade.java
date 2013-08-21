package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.ListenerWrapper;
import com.undeadscythes.udsplugin.RegionFlag;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Fired when a block melts.
 * 
 * @author UndeadScythes
 */
public class BlockFade extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final BlockFadeEvent event) {
        final Block block = event.getBlock();
        if(!hasFlag(block.getLocation(), RegionFlag.SNOW)) {
            event.setCancelled(true);
        }
    }
}
