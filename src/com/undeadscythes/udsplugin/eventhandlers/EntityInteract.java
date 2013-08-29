package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.regions.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * Fired when an entity interacts with the world.
 * 
 * @author UndeadScythes
 */
public class EntityInteract extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final EntityInteractEvent event) {
        final Block block = event.getBlock();
        event.setCancelled(hasFlag(block.getLocation(), RegionFlag.PROTECTION) && block.getType() == Material.SOIL);
    }
}
