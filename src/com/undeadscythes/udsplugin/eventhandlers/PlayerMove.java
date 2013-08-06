package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * Fired when a player moves.
 * If the player is marked as AFK it will toggle the setting.
 * 
 * @author UndeadScythes
 */
public class PlayerMove extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final PlayerMoveEvent event) {
        final SaveablePlayer player = PlayerUtils.getOnlinePlayer(event.getPlayer().getName());
        if(player.isAfk()) {
            player.toggleAfk();
            Bukkit.broadcastMessage(Color.TEXT + "*" + player.getNick() + " is no longer afk.");
        }
    }
}
