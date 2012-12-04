package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * When a player shears a sheep.
 * @author UndeadScythes
 */
public class PlayerShearEntity extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerShearEntityEvent event) {
        final SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName());
        if(!player.canBuildHere(event.getEntity().getLocation())) {
            event.setCancelled(true);
            player.sendMessage(Color.ERROR + "You cannot shear this animal.");
        }
    }
}
