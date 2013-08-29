package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class AfkCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(notPinned() && notNearMobs()) {
            if(player.toggleAfk()) {
                Bukkit.broadcastMessage(Color.TEXT + "*" + player.getNick() + " is now afk.");
            } else {
                Bukkit.broadcastMessage(Color.TEXT + "*" + player.getNick() + " is no longer afk.");
            }
        }
    }
}
