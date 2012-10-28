package com.undeadscythes.udsplugin1.eventhandlers;

import com.undeadscythes.udsplugin1.Color;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.UDSPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Description
 * @author UndeadScythes
 */
public class PlayerQuit implements Listener {
    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        ExtendedPlayer player = UDSPlugin.getOnlinePlayers().get(playerName);
        event.setQuitMessage(Color.BROADCAST + player.getDisplayName() + (player.isInClan() ? " of " + player.getClan() : "") + " has left.");
        UDSPlugin.getOnlinePlayers().remove(playerName);
    }
}
