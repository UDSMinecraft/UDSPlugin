package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * A creature spawns.
 * @author UndeadScythes
 */
public class CreatureSpawn extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(CreatureSpawnEvent event) {
        if(Config.HOSTILE_MOBS.contains(event.getEntityType()) && !hasMobs(event.getLocation())) {
            event.setCancelled(true);
        }
    }
}
