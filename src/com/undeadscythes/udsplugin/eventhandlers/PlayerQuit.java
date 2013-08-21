package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * Fired when a player leaves the server.
 *
 * @author UndeadScythes
 */
public class PlayerQuit implements Listener {
    @EventHandler
    public void onEvent(final PlayerQuitEvent event) {
        final String name = event.getPlayer().getName();
        final Member player = PlayerUtils.getOnlinePlayer(event.getPlayer());
        final long time = player.getLastPlayed();
        player.addTime(System.currentTimeMillis() - (time == 0 ? System.currentTimeMillis() : time));
        if(player.isHidden()) {
            for(final Member hiddenPlayer : PlayerUtils.getHiddenPlayers()) {
                try {
                    PlayerUtils.getOnlinePlayer(hiddenPlayer.getName()).sendWhisper(player.getNick() + " has left.");
                } catch (PlayerNotOnlineException ex) {}
            }
        } else {
            try {
                event.setQuitMessage(Color.CONNECTION + player.getNick() + (player.isInClan() ? " of " + player.getClan().getName() : "") + " has left.");
            } catch (NoMetadataSetException ex) {}
        }
        PlayerUtils.removeOnlinePlayer(name);
    }
}
