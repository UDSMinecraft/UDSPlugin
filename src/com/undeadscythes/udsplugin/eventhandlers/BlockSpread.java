package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Fired when fire or mushrooms spread.
 * 
 * @author UndeadScythes
 */
public class BlockSpread extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final BlockSpreadEvent event) {
        if(event.getSource().getType().equals(Material.FIRE) && !hasFlag(event.getNewState().getLocation(), RegionFlag.FIRE)) {
            event.setCancelled(true);
        } else if(event.getSource().getType().equals(Material.RED_MUSHROOM) || event.getSource().getType().equals(Material.BROWN_MUSHROOM) && !hasFlag(event.getNewState().getLocation(), RegionFlag.FOOD)) {
            event.setCancelled(true);
        }
    }
}
