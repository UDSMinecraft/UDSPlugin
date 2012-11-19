package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.Region.RegionFlag;
import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

/**
 * A block sets on fire.
 * @author UndeadScythes
 */
public class BlockIgnite extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(BlockIgniteEvent event) {
        if((event.getCause().equals(IgniteCause.FLINT_AND_STEEL) || event.getCause().equals(IgniteCause.FIREBALL))) {
            SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName());
            if(!player.canBuildHere(event.getBlock().getLocation())) {
                player.sendMessage(Message.CANT_BUILD_HERE);
                event.setCancelled(true);
            }
        } else if(!hasFlag(event.getBlock().getLocation(), RegionFlag.FIRE)) {
            event.setCancelled(true);
        }
    }
}
