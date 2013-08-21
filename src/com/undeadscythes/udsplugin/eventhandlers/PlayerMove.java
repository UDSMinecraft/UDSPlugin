package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.ListenerWrapper;
import com.undeadscythes.udsplugin.Member;
import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * @author UndeadScythes
 */
public class PlayerMove extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerMoveEvent event) {
        final Member player = PlayerUtils.getOnlinePlayer(event.getPlayer());
        if(player.isAfk()) {
            player.toggleAfk();
            Bukkit.broadcastMessage(Color.TEXT + "*" + player.getNick() + " is no longer afk.");
        }
    }
}
