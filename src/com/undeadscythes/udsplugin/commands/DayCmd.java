package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * Switches the current world to sunset.
 * @author UndeadScythes
 */
public class DayCmd extends PlayerCommandExecutor {
    /**
     * @inheritDoc
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsEq(0)) {
            player.getWorld().setTime(0);
            Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " summoned the sun.");
        }
    }
}
