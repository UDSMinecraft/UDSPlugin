package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;

/**
 * Server related commands.
 * @author UndeadScythes
 */
public class ServerCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(numArgsHelp(1)) {
            if(subCmdEquals("stop")) {
                for(SaveablePlayer target : PlayerUtils.getOnlinePlayers()) {
                    target.kickPlayer("Server is shutting down.");
                }
                Bukkit.shutdown();
            } else if(subCmdEquals("reload")) {
                Config.reload();
                player().sendNormal("Configuration file reloaded.");
            } else if(subCmdEquals("info")) {
                player().sendNormal("Server is running UDSPlugin version " + UDSPlugin.getVersion() + ".");           
            } else if(subCmdEquals("players")) {   
                player().sendNormal("There have been " + PlayerUtils.numPlayers() + " unique visitors.");
                player().sendNormal("There are " + PlayerUtils.numActives() + " active players.");
            } else if(subCmdEquals("setspawn")) {
                final Location location = player().getLocation();
                player().getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
                UDSPlugin.getData().setSpawn(location);
                player().sendNormal("Spawn location moved.");
            } else {
                subCmdHelp();
            }
        }
    }
}
