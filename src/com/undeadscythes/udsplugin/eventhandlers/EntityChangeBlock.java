package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.Region.RegionFlag;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * Description.
 * @author UndeadScythes
 */
public class EntityChangeBlock extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(EntityChangeBlockEvent event) {
        Entity entity = event.getEntity();
        event.setCancelled((entity instanceof Enderman || entity instanceof Silverfish || entity instanceof Wither) && hasFlag(event.getBlock().getLocation(), RegionFlag.PROTECTION));
    }
}
