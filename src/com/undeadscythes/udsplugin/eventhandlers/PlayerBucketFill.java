package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.ListenerWrapper;
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
    public final void onEvent(final PlayerBucketFillEvent event) {
        event.setCancelled(!PlayerUtils.getOnlinePlayer(event.getPlayer()).canBuildHere(event.getBlockClicked().getLocation()));
    }
}
