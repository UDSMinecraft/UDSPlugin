package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.Color;
import org.bukkit.*;

/**
 * Handles various MultiVerse-like functions.
 * @author UndeadScythes
 */
public class WorldCmd extends CommandWrapper {
    @Override
    public final void playerExecute() {
        final String subCmd = args[0].toLowerCase();
        if(args.length == 1) {
            if(subCmd.equals("list")) {
                player.sendMessage(Color.MESSAGE + "Available worlds:");
                String worldList = "";
                for(World world : Bukkit.getWorlds()) {
                    worldList = worldList.concat(", " + world.getName());
                }
                player.sendMessage(Color.TEXT + worldList.substring(2));
            } else if(subCmd.equals("setspawn")) {
                final Location location = player.getLocation();
                if(player.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                    player.sendMessage(Color.MESSAGE + "Spawn location of world " + player.getWorld().getName() + " set.");
                } else {
                    player.sendMessage(Color.ERROR + "Could not set spawn location.");
                }
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(2)) {
            if(subCmd.equals("tp")) {
                final World world = Bukkit.getWorld(args[1]);
                if(world != null) {
                    player.teleport(world.getSpawnLocation());
                } else {
                    player.sendMessage(Color.ERROR + "That world does not exist.");
                }
            } else if(subCmd.equals("create")) {
                if(noCensor(args[1]) && noWorld(args[1])) {
                    player.sendMessage(Color.MESSAGE + "Generating spawn area...");
                    Bukkit.createWorld(new WorldCreator(args[1]));
                    UDSPlugin.getData().newWorld(args[1]);
                    player.sendMessage(Color.MESSAGE + "World created.");
                }
            } else {
                subCmdHelp();
            }
        }
    }

    private boolean noWorld(final String world) {
        if(Bukkit.getWorld(world) != null) {
            player.sendMessage(Color.ERROR + "A world already exists with that name.");
            return false;
        } else {
            return true;
        }
    }
}
