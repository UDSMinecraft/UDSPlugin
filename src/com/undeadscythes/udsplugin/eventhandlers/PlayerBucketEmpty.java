package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.ListenerWrapper;
import com.undeadscythes.udsplugin.Member;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * @author UndeadScythes
 */
public class PlayerBucketEmpty extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerBucketEmptyEvent event) {
        final Member player = PlayerUtils.getOnlinePlayer(event.getPlayer());
        final Location location1 = event.getBlockClicked().getLocation();
        final Location location2 = event.getBlockClicked().getRelative(event.getBlockFace()).getLocation();
        event.setCancelled(!player.canBuildHere(location1) || !player.canBuildHere(location2));
    }
}
