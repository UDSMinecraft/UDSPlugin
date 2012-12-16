package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * When an entity changes a block.
 * @author UndeadScythes
 */
public class EntityChangeBlock extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final EntityChangeBlockEvent event) {
        final Entity entity = event.getEntity();
        event.setCancelled((entity instanceof Enderman || entity instanceof Silverfish || entity instanceof Wither) && hasFlag(event.getBlock().getLocation(), RegionFlag.PROTECTION));
    }
}
