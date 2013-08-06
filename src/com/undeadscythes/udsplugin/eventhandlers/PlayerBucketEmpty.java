package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * Fired when a player empties a bucket.
 * 
 * @author UndeadScythes
 */
public class PlayerBucketEmpty extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final PlayerBucketEmptyEvent event) {
        final SaveablePlayer player = PlayerUtils.getOnlinePlayer(event.getPlayer().getName());
        final Location location1 = event.getBlockClicked().getLocation();
        final Location location2 = event.getBlockClicked().getRelative(event.getBlockFace()).getLocation();
        event.setCancelled(!player.canBuildHere(location1) || !player.canBuildHere(location2));
    }
}
