package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import java.io.*;
import org.bukkit.*;
import org.bukkit.entity.*;

/**
 * Handles various MultiVerse-like functions.
 * @author UndeadScythes
 */
public class WorldCmd extends CommandWrapper {
    @Override
    public final void playerExecute() {
        if(args.length == 1) {
            if("list".equals(subCmd)) {
                player.sendMessage(Color.MESSAGE + "Available worlds:");
                String worldList = "";
                for(World world : Bukkit.getWorlds()) {
                    worldList = worldList.concat(", " + world.getName());
                }
                player.sendMessage(Color.TEXT + worldList.substring(2));
            } else if("setspawn".equals(subCmd)) {
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
            if("tp".equals(subCmd)) {
                final World world = Bukkit.getWorld(args[1]);
                if(world != null) {
                    player.teleport(world.getSpawnLocation());
                } else {
                    player.sendMessage(Color.ERROR + "That world does not exist.");
                }
            } else if("create".equals(subCmd)) {
                if(noCensor(args[1]) && noWorld(args[1])) {
                    player.sendMessage(Color.MESSAGE + "Generating spawn area...");
                    Bukkit.createWorld(new WorldCreator(args[1]));
                    UDSPlugin.getData().newWorld(args[1]);
                    player.sendMessage(Color.MESSAGE + "World created.");
                }
            } else if("forget".equals(subCmd)) {
                if(args[1].equals(UDSPlugin.getConfigString(ConfigRef.MAIN_WORLD))) {
                    player.sendMessage(Color.ERROR + "This world cannot be forgotten.");
                }
                final World world = getWorld(args[1]);
                if(world != null) {
                    UDSPlugin.getData().getWorlds().remove(args[1]);
                    player.sendMessage(Color.MESSAGE + "World forgotten.");
                }
            } else if("delete".equals(subCmd)) {
                if(args[1].equals(UDSPlugin.getConfigString(ConfigRef.MAIN_WORLD))) {
                    player.sendMessage(Color.ERROR + "This world cannot be deleted.");
                }
                final World world = getWorld(args[1]);
                if(world != null) {
                    for(Player worldPlayer : world.getPlayers()) {
                        worldPlayer.sendMessage(Color.MESSAGE + "That world is no longer safe.");
                        worldPlayer.teleport(UDSPlugin.getData().getSpawn());
                    }
                    UDSPlugin.getData().getWorlds().remove(args[1]);
                    final String worldName = world.getName();
                    Bukkit.unloadWorld(worldName, false);
                    final File file = new File(worldName);
                    if(deleteFile(file)) {
                        player.sendMessage(Color.MESSAGE + "World deleted.");
                    } else {
                        player.sendMessage(Color.ERROR + "World files could not be completely removed.");
                    }
                }
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(3)) {
            if("flag".equals(subCmd)) {
                flag();
            } else {
                subCmdHelp();
            }
        }
    }
    
    private void flag() {
        final World world = getWorld(args[1]);
        final RegionFlag flag = getFlag(args[2]);
        if(world != null && flag != null) {
            player.sendMessage(Color.MESSAGE + world.getName() + " flag " + flag.toString() + " now set to " + UDSPlugin.toggleWorldFlag(world, flag) + ".");
        }
    }

    private boolean deleteFile(final File file) {
        if(file.delete()) {
            return true;
        } else {
            if(file.isDirectory()) {
                for(File subFile : file.listFiles()) {
                    subFile.delete();
                }
            }
        }
        return file.delete();
    }

    private boolean noWorld(final String world) {
        if(Bukkit.getWorld(world) != null) {
            player.sendMessage(Color.ERROR + "A world already exists with that name.");
            return false;
        } else {
            return true;
        }
    }

    private World getWorld(final String name) {
        final World world = Bukkit.getWorld(name);
        if(world == null) {
            player.sendMessage(Color.ERROR + "That world does not exist.");
        }
        return world;
    }
}
