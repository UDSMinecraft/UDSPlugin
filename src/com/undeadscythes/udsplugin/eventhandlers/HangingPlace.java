package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.hanging.*;

/**
 * A player places a painting or item frame.
 * @author UndeadScythes
 */
public class HangingPlace extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(HangingPlaceEvent event) {
        SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName());
        if(!player.canBuildHere(event.getBlock().getLocation())) {
            event.setCancelled(true);
            player.sendMessage(Message.CANT_BUILD_HERE);
        }
    }
}