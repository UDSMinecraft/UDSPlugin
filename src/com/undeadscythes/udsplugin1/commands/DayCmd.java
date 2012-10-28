package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.Color;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;
import org.bukkit.Bukkit;

/**
 *
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
            Bukkit.getServer().broadcastMessage(Color.BROADCAST + player.getDisplayName() + " summoned the sun.");
        }
    }
}
