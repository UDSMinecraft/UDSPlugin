package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * Description
 * @author UndeadScythes
 */
public class PlayerQuit implements Listener {
    @EventHandler
    public void onEvent(final PlayerQuitEvent event) {
        final String playerName = event.getPlayer().getName();
        final SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(playerName);
        player.addTime(System.currentTimeMillis() - player.getLastPlayed());
        event.setQuitMessage(Color.BROADCAST + player.getNick() + (player.isInClan() ? " of " + player.getClan() : "") + " has left.");
        UDSPlugin.getOnlinePlayers().remove(playerName);
    }
}
