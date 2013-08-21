package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

/**
 * @author UndeadScythes
 */
public class BlockIgnite extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final BlockIgniteEvent event) {
        if((event.getCause().equals(IgniteCause.FLINT_AND_STEEL) || event.getCause().equals(IgniteCause.FIREBALL))) {
            final Member player = PlayerUtils.getOnlinePlayer(event.getPlayer());
            if(!player.canBuildHere(event.getBlock().getLocation())) {
                player.sendMessage(Message.CANT_BUILD_HERE);
                event.setCancelled(true);
            }
        } else if(!hasFlag(event.getBlock().getLocation(), RegionFlag.FIRE)) {
            event.setCancelled(true);
        }
    }
}
