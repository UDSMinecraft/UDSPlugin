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
    public void onEvent(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(playerName);
        player.addTime(System.currentTimeMillis() - player.getLastPlayed());
        event.setQuitMessage(Color.BROADCAST + player.getDisplayName() + (player.isInClan() ? " of " + player.getClan() : "") + " has left.");
        UDSPlugin.getOnlinePlayers().remove(playerName);
    }
}
