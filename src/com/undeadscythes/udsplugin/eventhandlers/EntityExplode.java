package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * When an entity explodes.
 * @author UndeadScythes
 */
public class EntityExplode extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final EntityExplodeEvent event) {
        if(event.getEntityType().equals(EntityType.CREEPER) && UDSPlugin.getConfigBool(ConfigRef.BLOCK_CREEPERS)) {
            event.blockList().clear();
        } else if(event.getEntityType().equals(EntityType.PRIMED_TNT) && UDSPlugin.getConfigBool(ConfigRef.BLOCK_TNT)) {
            event.blockList().clear();
        } else if((event.getEntityType().equals(EntityType.WITHER_SKULL) || event.getEntityType().equals(EntityType.WITHER)) && UDSPlugin.getConfigBool(ConfigRef.BLOCK_WITHER)) {
            event.blockList().clear();
        }
    }
}
