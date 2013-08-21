package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.ListenerWrapper;
import com.undeadscythes.udsplugin.RegionFlag;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Fired when a block is destroyed by fire.
 * 
 * @author UndeadScythes
 */
public class BlockBurn extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final BlockBurnEvent event) {
        if(!hasFlag(event.getBlock().getLocation(), RegionFlag.FIRE)) {
            event.setCancelled(true);
        }
    }
}
