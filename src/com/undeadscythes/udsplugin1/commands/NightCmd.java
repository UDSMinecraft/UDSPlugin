package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.Color;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;
import org.bukkit.Bukkit;

/**
 * Set the world the players is in to night.
 * @author UndeadScythes
 */
public class NightCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(hasPerm("night")) {
            player.getWorld().setTime(14000);
            Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " summoned the moon.");
        }
    }

}
