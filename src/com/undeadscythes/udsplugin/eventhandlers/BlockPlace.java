package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Fired when a player places a block.
 * @author UndeadScythes
 */
public class BlockPlace extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final BlockPlaceEvent event) {
        final SaveablePlayer player = PlayerUtils.getOnlinePlayer(event.getPlayer().getName());
        if(!player.canBuildHere(event.getBlock().getLocation())) {
            player.sendMessage(Message.CANT_BUILD_HERE);
            event.setCancelled(true);
        }
    }
}
