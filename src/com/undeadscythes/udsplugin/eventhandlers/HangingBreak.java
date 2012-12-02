package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.Region.RegionFlag;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.hanging.*;

/**
 * When a painting gets broken.
 * @author UndeadScythes
 */
public class HangingBreak extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final HangingBreakByEntityEvent event) {
        final Entity remover = event.getRemover();
        final Location location = event.getEntity().getLocation();
        if(getAbsoluteEntity(remover) instanceof Player) {
            final SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(((Player)remover).getName());
            if(!player.canBuildHere(location)) {
                event.setCancelled(true);
                player.sendMessage(Message.CANT_BUILD_HERE);
            }
        } else if(hasFlag(location, RegionFlag.PROTECTION)) {
            event.setCancelled(true);
        }
    }
}
