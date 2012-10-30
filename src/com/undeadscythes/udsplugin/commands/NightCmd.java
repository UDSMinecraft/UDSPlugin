package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

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
        if(argsEq(0)) {
            player.getWorld().setTime(14000);
            Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " summoned the moon.");
        }
    }

}
