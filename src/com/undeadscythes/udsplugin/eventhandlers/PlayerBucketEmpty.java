package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.members.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * @author UndeadScythes
 */
public class PlayerBucketEmpty extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerBucketEmptyEvent event) {
        final Member player = MemberUtils.getOnlineMember(event.getPlayer());
        final Location location1 = event.getBlockClicked().getLocation();
        final Location location2 = event.getBlockClicked().getRelative(event.getBlockFace()).getLocation();
        event.setCancelled(!player.canBuildHere(location1) || !player.canBuildHere(location2));
    }
}
