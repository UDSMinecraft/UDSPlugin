package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * Fired when a player teleports.
 *
 * @author UndeadScythes
 */
public class PlayerTeleport extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerTeleportEvent event) {
        final World from = event.getFrom().getWorld();
        final World to = event.getTo().getWorld();
        if(from == null || to == null) {
            event.setCancelled(true);
            return;
        }
        if(!from.equals(to)) {
            MemberUtils.updateMembers();
            if(!MemberUtils.getOnlineMember(event.getPlayer()).hasPerm(Perm.SHAREDINV)) {
                for(String string : Config.SHARES) {
                    List<String> shares = Arrays.asList(string.split(","));
                    if(!shares.contains(from.getName()) || !shares.contains(to.getName())) {
                        MemberUtils.saveInventory(MemberUtils.getOnlineMember(event.getPlayer()), from);
                        MemberUtils.loadInventory(MemberUtils.getOnlineMember(event.getPlayer()), to);
                    }
                }
                GameMode mode = UDSPlugin.getWorldMode(to);
                if(mode != null && !MemberUtils.getOnlineMember(event.getPlayer()).hasPerm(Perm.ANYMODE)) {
                    event.getPlayer().setGameMode(mode);
                }
            }
        }
    }
}