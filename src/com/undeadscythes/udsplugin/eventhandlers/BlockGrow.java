package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.Region.RegionFlag;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * When a block grows.
 * @author UndeadScythes
 */
public class BlockGrow extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(BlockGrowEvent event) {
        if(!hasFlag(event.getNewState().getLocation(), RegionFlag.VINES)) {
            event.setCancelled(true);
        }
    }
}
