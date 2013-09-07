package com.undeadscythes.udsplugin.eventhandlers;

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * Fired when a player's client is logging into the server.
 * 
 * @author UndeadScythes
 */
public class PlayerLogin implements Listener {
    @EventHandler
    public void onEvent(final PlayerLoginEvent event) {
        if(Bukkit.hasWhitelist() && !event.getPlayer().isWhitelisted()) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "The server is currently whitelisted while we make major improvements. Thank you for your patience.");
        }
    }
}
