package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;


/**
 * Server related commands.
 * @author UndeadScythes
 */
public class ServerCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        if(numArgsHelp(1)) {
            if(args[0].equals("stop")) {
                for(SaveablePlayer target : UDSPlugin.getOnlinePlayers().values()) {
                    target.kickPlayer("Server is shutting down.");
                }
                Bukkit.shutdown();
            } else if(args[0].equals("reload")) {
                Config.reload();
                player.sendMessage(Color.MESSAGE + "Configuration file reloaded.");
            } else {
                subCmdHelp();
            }
        }
    }
}
