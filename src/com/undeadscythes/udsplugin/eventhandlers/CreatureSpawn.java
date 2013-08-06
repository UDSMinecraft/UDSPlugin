package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * Fired when a creature spawns.
 * 
 * @author UndeadScythes
 */
public class CreatureSpawn extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final CreatureSpawnEvent event) {
        if(UDSPlugin.isHostileMob(event.getEntityType()) && !hasFlag(event.getLocation(), RegionFlag.MOBS)) {
            event.setCancelled(true);
        }
    }
}
