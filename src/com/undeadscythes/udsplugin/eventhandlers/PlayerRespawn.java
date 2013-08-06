package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * Fired when a player respawns.
 * 
 * @author UndeadScythes
 */
public class PlayerRespawn extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final PlayerRespawnEvent event) {
        final SaveablePlayer player = PlayerUtils.getOnlinePlayer(event.getPlayer().getName());
        if(player.getBedSpawnLocation() == null || !player.getBedSpawnLocation().getWorld().equals(player.getWorld())) {
            final Region home = RegionUtils.getRegion(RegionType.HOME, player.getName() + "home");
            if(home != null && home.getWorld().equals(player.getWorld())) {
                event.setRespawnLocation(LocationUtils.findSafePlace(home.getWarp()));
            } else {
                event.setRespawnLocation(player.getWorld().getSpawnLocation());
            }
        }
        if(player.hasPermission(Perm.BACK_ON_DEATH)) {
            player.sendNormal("Use /back to return to where you died.");
        }
        PlayerUtils.loadInventory(player);
    }
}
