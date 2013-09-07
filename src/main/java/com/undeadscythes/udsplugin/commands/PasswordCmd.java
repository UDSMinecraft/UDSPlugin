package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.utilities.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class PasswordCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        try {
            player.sendNormal("Your password is '" + PasswordUtils.getPassword(player.getName()) + "'.");
        } catch(PasswordRetrievalException ex) {
            Bukkit.getLogger().info(ex.getMessage());
            player.sendNormal("Your password cannot be retrieved at this time.");
        }
    }
}
