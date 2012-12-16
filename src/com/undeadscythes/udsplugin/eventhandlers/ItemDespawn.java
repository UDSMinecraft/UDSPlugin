package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.*;

/**
 * An item despawns.
 * @author UndeadScythes
 */
public class ItemDespawn extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final ItemDespawnEvent event) {
        final ItemStack item = event.getEntity().getItemStack();
        if(item.getType().equals(Material.SAPLING)) {
            final Location location = event.getLocation();
            final Block block = location.getWorld().getBlockAt(location);
            final Material blockUnder = block.getRelative(BlockFace.DOWN).getType();
            if(!hasFlag(location, RegionFlag.PROTECTION) && (blockUnder.equals(Material.DIRT) && blockUnder.equals(Material.GRASS))) {
                block.setTypeIdAndData(Material.SAPLING.getId(), item.getData().getData(), true);
            }
        }
    }
}
