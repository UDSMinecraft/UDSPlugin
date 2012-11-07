package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * Description.
 * @author UndeadScythes
 */
public class EntityExplode extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(EntityExplodeEvent event) {
        if(event.getEntityType().equals(EntityType.CREEPER) && Config.BLOCK_CREEPER) {
            event.blockList().clear();
        } else if(event.getEntityType().equals(EntityType.PRIMED_TNT) && Config.BLOCK_TNT) {
            event.blockList().clear();
        } else if((event.getEntityType().equals(EntityType.WITHER_SKULL) || event.getEntityType().equals(EntityType.WITHER)) && Config.BLOCK_WITHER) {
            event.blockList().clear();
        }
    }
}
