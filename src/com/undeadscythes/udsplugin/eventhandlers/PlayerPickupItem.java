package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * A block melts.
 * @author UndeadScythes
 */
public class PlayerPickupItem extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final PlayerPickupItemEvent event) {
        if(UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName()).isHidden()) {
            event.setCancelled(true);
        }
    }
}
