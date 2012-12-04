package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.Region.RegionFlag;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * A creature spawns.
 * @author UndeadScythes
 */
public class CreatureSpawn extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final CreatureSpawnEvent event) {
        if(Config.HOSTILE_MOBS.contains(event.getEntityType()) && !hasFlag(event.getLocation(), RegionFlag.MOBS)) {
            event.setCancelled(true);
        }
    }
}
