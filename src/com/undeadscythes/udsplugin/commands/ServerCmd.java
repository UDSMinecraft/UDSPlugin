package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class ServerCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(numArgsHelp(1)) {
            if(subCmdEquals("stop")) {
                for(Member target : MemberUtils.getOnlineMembers()) {
                    target.kickPlayer("Server is shutting down.");
                }
                Bukkit.shutdown();
            } else if(subCmdEquals("reload")) {
                Config.reload();
                player.sendNormal("Configuration file reloaded.");
            } else if(subCmdEquals("info")) {
                player.sendNormal("Server is running UDSPlugin version " + UDSPlugin.getVersion() + ".");
            } else if(subCmdEquals("players")) {
                player.sendNormal("There have been " + MemberUtils.countMembers() + " unique visitors.");
                player.sendNormal("There are " + MemberUtils.countActiveMembers() + " active players.");
            } else if(subCmdEquals("setspawn")) {
                final Location location = player.getLocation();
                player.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
                UDSPlugin.getData().spawn = location;
                player.sendNormal("Spawn location moved.");
            } else {
                subCmdHelp();
            }
        }
    }
}
