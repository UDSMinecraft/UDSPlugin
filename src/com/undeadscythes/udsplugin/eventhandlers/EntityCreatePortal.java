package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.World.Environment;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * Fired when a portal is created by an entity.
 * 
 * @author UndeadScythes
 */
public class EntityCreatePortal extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final EntityCreatePortalEvent event) {
        if(event.getPortalType().equals(PortalType.ENDER) && event.getBlocks().get(0).getWorld().getEnvironment().equals(Environment.THE_END)) {
            event.setCancelled(true);
        }
    }
}
