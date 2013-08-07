package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * Fired when a player picks up an item.
 * 
 * @author UndeadScythes
 */
public class PlayerPickupItem extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final PlayerPickupItemEvent event) {
        final SaveablePlayer player = PlayerUtils.getOnlinePlayer(event.getPlayer());
        event.setCancelled(player.isHidden() || player.isShopping());
    }
}
