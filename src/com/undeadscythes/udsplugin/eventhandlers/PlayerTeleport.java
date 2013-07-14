package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.ListenerWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Triggered when a player teleports.
 * @author Dave
 */
public class PlayerTeleport extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerTeleportEvent event) {
        
    }
}
