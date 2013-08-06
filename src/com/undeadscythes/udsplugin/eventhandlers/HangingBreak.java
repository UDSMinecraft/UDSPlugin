package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.hanging.*;

/**
 * Fired when a painting or item frame gets broken.
 * 
 * @author UndeadScythes
 */
public class HangingBreak extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final HangingBreakByEntityEvent event) {
        final Entity remover = event.getRemover();
        final Location location = event.getEntity().getLocation();
        if(getAbsoluteEntity(remover) instanceof Player) {
            final SaveablePlayer player = PlayerUtils.getOnlinePlayer(((Player)remover).getName());
            if(!player.canBuildHere(location)) {
                event.setCancelled(true);
                player.sendNormal(Message.CANT_BUILD_HERE);
            }
        } else if(hasFlag(location, RegionFlag.PROTECTION)) {
            event.setCancelled(true);
        }
    }
}
