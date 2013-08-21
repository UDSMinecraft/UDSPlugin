package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * @author UndeadScythes
 */
public class PlayerShearEntity extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerShearEntityEvent event) {
        final Member player = PlayerUtils.getOnlinePlayer(event.getPlayer());
        if(!player.canBuildHere(event.getEntity().getLocation())) {
            event.setCancelled(true);
            player.sendError("You cannot shear this animal.");
        }
    }
}
