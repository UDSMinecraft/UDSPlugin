package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * When a player enters a portal.
 * @author UndeadScythes
 */
public class PlayerPortal extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerPortalEvent event) {
        final Player player = event.getPlayer();
        final Portal portal = findPortal(player.getLocation());
        if(portal != null) {
            player.teleport(portal.getWarp().getLocation());
        }
    }
}
