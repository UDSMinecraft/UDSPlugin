package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * Fired when an entity explodes.
 * 
 * @author UndeadScythes
 */
public class EntityExplode extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final EntityExplodeEvent event) {
        if(event.getEntityType().equals(EntityType.CREEPER) && Config.BLOCK_CREEPERS) {
            event.blockList().clear();
        } else if((event.getEntityType().equals(EntityType.PRIMED_TNT) || event.getEntityType().equals(EntityType.MINECART_TNT)) && Config.BLOCK_TNT) {
            event.blockList().clear();
        } else if((event.getEntityType().equals(EntityType.WITHER_SKULL) || event.getEntityType().equals(EntityType.WITHER)) && Config.BLOCK_WITHER) {
            event.blockList().clear();
        }
    }
}
