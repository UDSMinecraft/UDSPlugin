package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.Region.RegionFlag;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * A block melts.
 * @author UndeadScythes
 */
public class BlockFade extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final BlockFadeEvent event) {
        if(!hasFlag(event.getBlock().getLocation(), RegionFlag.SNOW)) {
            event.setCancelled(true);
        }
    }
}
