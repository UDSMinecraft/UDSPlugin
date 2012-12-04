package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * Server related commands.
 * @author UndeadScythes
 */
public class ServerCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        if(numArgsHelp(1) && args[0].equals("stop")) {
            for(SaveablePlayer target : UDSPlugin.getOnlinePlayers().values()) {
                target.kickPlayer("Server is shutting down.");
            }
            Bukkit.shutdown();
        }
    }
}
