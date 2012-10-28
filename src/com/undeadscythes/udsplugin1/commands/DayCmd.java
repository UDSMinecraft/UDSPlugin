package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.Color;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;
import org.bukkit.Bukkit;

/**
 * Switches the current world to sunset.
 * @author UndeadScythes
 */
public class DayCmd extends PlayerCommandExecutor {
    /**
     * @inheritDoc
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(hasPerm("day")) {
            player.getWorld().setTime(0);
            Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " summoned the sun.");
        }
    }
}
