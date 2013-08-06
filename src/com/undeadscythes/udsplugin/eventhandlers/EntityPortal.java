package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * Fired when an entity teleports via a portal.
 * 
 * @author UndeadScythes
 */
public class EntityPortal extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final EntityPortalEvent event) {
        final Entity entity = event.getEntity();
        final Portal portal = findPortal(entity.getLocation());
        if(portal != null) {
            if(!portal.crossWorld()) {
                portal.warp(entity);
            }
            event.setCancelled(true);
        }
    }
}
