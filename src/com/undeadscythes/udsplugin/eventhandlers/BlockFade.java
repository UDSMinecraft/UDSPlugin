package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.block.Block;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * A block melts.
 * @author UndeadScythes
 */
public class BlockFade extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final BlockFadeEvent event) {
        final Block block = event.getBlock();
        if(!hasFlag(block.getLocation(), RegionFlag.SNOW)) {
            event.setCancelled(true);
        }
    }
}
