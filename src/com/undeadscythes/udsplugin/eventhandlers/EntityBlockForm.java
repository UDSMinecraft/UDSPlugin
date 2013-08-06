package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Fired when an entity forms a block.
 * 
 * @author UndeadScythes
 */
public class EntityBlockForm extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final EntityBlockFormEvent event) {
        if(event.getEntity() instanceof Snowman && !hasFlag(event.getNewState().getLocation(), RegionFlag.SNOW)) {
            event.setCancelled(true);
        }
    }
}
