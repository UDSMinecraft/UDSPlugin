package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Fire or mushrooms spread.
 * @author UndeadScythes
 */
public class BlockSpread extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(BlockSpreadEvent event) {
        if(event.getSource().getType().equals(Material.FIRE) && !hasFire(event.getNewState().getLocation())) {
            event.setCancelled(true);
        } else if(event.getSource().getType().equals(Material.RED_MUSHROOM) || event.getSource().getType().equals(Material.BROWN_MUSHROOM) && !hasFood(event.getNewState().getLocation())) {
            event.setCancelled(true);
        }
    }
}
