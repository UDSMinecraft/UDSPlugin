package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class ServerCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(numArgsHelp(1)) {
            if(subCmdEquals("stop")) {
                for(Member target : PlayerUtils.getOnlinePlayers()) {
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
                player().sendNormal("There are " + PlayerUtils.numActivePlayers() + " active players.");
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
