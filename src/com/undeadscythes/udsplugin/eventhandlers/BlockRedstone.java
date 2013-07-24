package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * When a block is powered by redstone.
 * @author UndeadScythes
 */
public class BlockRedstone extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final BlockRedstoneEvent event) {
        final Block block = event.getBlock();
        if(block.getType().equals(Material.REDSTONE_LAMP_ON) && hasFlag(block.getLocation(), RegionFlag.LAMP)) {
            event.setNewCurrent(5);
        }
    }
}
