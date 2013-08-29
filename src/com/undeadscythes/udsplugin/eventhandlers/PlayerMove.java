package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.Color;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * @author UndeadScythes
 */
public class PlayerMove extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerMoveEvent event) {
        final Member player = MemberUtils.getOnlineMember(event.getPlayer());
        if(player.isAfk()) {
            player.toggleAfk();
            Bukkit.broadcastMessage(Color.TEXT + "*" + player.getNick() + " is no longer afk.");
        }
    }
}
