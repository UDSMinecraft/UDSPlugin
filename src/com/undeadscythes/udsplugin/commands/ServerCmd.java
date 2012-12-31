package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;


/**
 * Server related commands.
 * @author UndeadScythes
 */
public class ServerCmd extends CommandWrapper {
    @Override
    public final void playerExecute() {
        if(numArgsHelp(1)) {
            if(args[0].equals("stop")) {
                for(SaveablePlayer target : UDSPlugin.getOnlinePlayers().values()) {
                    target.kickPlayer("Server is shutting down.");
                }
                Bukkit.shutdown();
            } else if(args[0].equals("reload")) {
                UDSPlugin.reloadConf();
                player.sendMessage(Color.MESSAGE + "Configuration file reloaded.");
            } else if(args[0].equals("info")) {
                player.sendMessage(Color.MESSAGE + "Server is running UDSPlugin version " + UDSPlugin.getVersion() + ".");
                player.sendMessage(Color.MESSAGE + "There have been " + UDSPlugin.getPlayers().size() + " unique visitors.");
            } else {
                subCmdHelp();
            }
        }
    }
}
