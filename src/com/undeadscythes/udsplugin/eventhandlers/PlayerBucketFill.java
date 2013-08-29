package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * Fired when a player fills a bucket.
 * 
 * @author UndeadScythes
 */
public class PlayerBucketFill extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerBucketFillEvent event) {
        event.setCancelled(!MemberUtils.getOnlineMember(event.getPlayer()).canBuildHere(event.getBlockClicked().getLocation()));
    }
}
