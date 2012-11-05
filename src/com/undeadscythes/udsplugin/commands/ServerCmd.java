package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * Server related commands.
 * @author UndeadScythes
 */
public class ServerCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsEq(1)) {
            if(args[0].equals("stop")) {
                for(SaveablePlayer target : UDSPlugin.getOnlinePlayers().values()) {
                    target.kickPlayer("Server is shutting down.");
                }
                Bukkit.shutdown();
            }
        }
    }
}
