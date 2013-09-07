package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.exceptions.*;
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
        final Member player = MemberUtils.getOnlineMember(event.getPlayer());
        final long time = player.getLastPlayed();
        player.addTime(System.currentTimeMillis() - (time == 0 ? System.currentTimeMillis() : time));
        if(player.isHidden()) {
            for(final OfflineMember hiddenPlayer : MemberUtils.getHiddenMembers()) {
                try {
                    MemberUtils.getOnlineMember(hiddenPlayer.getName()).sendWhisper(player.getNick() + " has left.");
                } catch(PlayerNotOnlineException ex) {}
            }
        } else {
            try {
                event.setQuitMessage(Color.CONNECTION + player.getNick() + (player.isInClan() ? " of " + player.getClan().getName() : "") + " has left.");
            } catch(NoMetadataSetException ex) {}
        }
        MemberUtils.removeOnlineMember(name);
        MemberUtils.updateMembers();
    }
}
