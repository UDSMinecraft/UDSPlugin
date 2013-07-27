package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
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
                player.sendNormal("Available worlds:");
                String worldList = "";
                for(World world : Bukkit.getWorlds()) {
                    worldList = worldList.concat(", " + world.getName());
                }
                player.sendText(worldList.substring(2));
            } else if("setspawn".equals(subCmd)) {
                final Location location = player.getLocation();
                if(player.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                    player.sendNormal("Spawn location of world " + player.getWorld().getName() + " set.");
                } else {
                    player.sendError("Could not set spawn location.");
                }
            } else if("info".equals(subCmd)) {
                info(player.getWorld());
            } else {
                subCmdHelp();
            }
        } else if(args.length == 2) {
            if("tp".equals(subCmd)) {
                final World world = Bukkit.getWorld(args[1]);
                if(world != null) {
                    player.teleport(world.getSpawnLocation());
                } else {
                    player.sendError("That world does not exist.");
                }
            } else if("create".equals(subCmd)) {
                if(noCensor(args[1]) && noWorld(args[1])) {
                    player.sendNormal("Generating spawn area...");
                    Bukkit.createWorld(new WorldCreator(args[1]));
                    UDSPlugin.getData().newWorld(args[1]);
                    player.sendNormal("World created.");
                }
            } else if("forget".equals(subCmd)) {
                if(args[1].equals(UDSPlugin.getConfigString(ConfigRef.MAIN_WORLD))) {
                    player.sendError("This world cannot be forgotten.");
                }
                final World world = getWorld(args[1]);
                if(world != null) {
                    UDSPlugin.getData().getWorlds().remove(args[1]);
                    player.sendNormal("World forgotten.");
                }
            } else if("delete".equals(subCmd)) {
                if(args[1].equals(UDSPlugin.getConfigString(ConfigRef.MAIN_WORLD))) {
                    player.sendError("This world cannot be deleted.");
                }
                final World world = getWorld(args[1]);
                if(world != null) {
                    for(Player worldPlayer : world.getPlayers()) {
                        PlayerUtils.getOnlinePlayer(worldPlayer.getName()).sendNormal("That world is no longer safe.");
                        worldPlayer.teleport(UDSPlugin.getData().getSpawn());
                    }
                    UDSPlugin.getData().getWorlds().remove(args[1]);
                    final String worldName = world.getName();
                    Bukkit.unloadWorld(worldName, false);
                    final File file = new File(worldName);
                    if(deleteFile(file)) {
                        player.sendNormal("World deleted.");
                    } else {
                        player.sendError("World files could not be completely removed.");
                    }
                }
            } else if("info".equals(subCmd)) {
                final World world = getWorld(args[1]);
                if(world != null) {
                    info(world);
                }
            } else if("flag".equals(subCmd)) {
                flag(player.getWorld(), args[1]);
            } else if("mode".equals(subCmd)) {
                mode(player.getWorld(), args[1]);
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(3)) {
            if("flag".equals(subCmd)) {
                final World world = getWorld(args[1]);
                if(world != null) {
                    flag(world, args[2]);
                }
            } else if("mode".equals(subCmd)) {
                final World world = getWorld(args[1]);
                if(world != null) {
                    mode(world, args[2]);
                }
            } else {
                subCmdHelp();
            }
        }
    }
    
    private void info(final World world) {
        player.sendNormal("World " + world.getName() + " info:");
        player.sendText("Game mode: " + UDSPlugin.getWorldMode(world).toString().toLowerCase());
        String flagString = "";
        for(RegionFlag test : RegionFlag.values()) {
            if(UDSPlugin.checkWorldFlag(world, test)) {
                flagString = flagString.concat(test.toString() + ", ");
            }
        }
        if("".equals(flagString)) {
            player.sendText("No flags.");
        } else {
            player.sendText("Flags: " + flagString.substring(0, flagString.length() - 2));
        }
    }
    
    private void flag(final World world, final String flagName) {
        final RegionFlag flag = getFlag(flagName);
        if(flag != null) {
            player.sendNormal(world.getName() + " flag " + flag.toString() + " now set to " + UDSPlugin.toggleWorldFlag(world, flag) + ".");
        }
    }
    
    private void mode(final World world, final String modeName) {
        final GameMode mode = getMode(modeName);
        if(mode != null) {
            UDSPlugin.changeWorldMode(world, mode);
            player.sendNormal(world.getName() + " game mode now set to " + mode.toString() + ".");
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
            player.sendError("A world already exists with that name.");
            return false;
        } else {
            return true;
        }
    }

    private World getWorld(final String name) {
        final World world = Bukkit.getWorld(name);
        if(world == null) {
            player.sendError("That world does not exist.");
        }
        return world;
    }
    
    private GameMode getMode(final String name) {
        GameMode mode = null;
        for(GameMode test : GameMode.values()) {
            if(test.toString().equals(name.toUpperCase())) {
                mode = test;
            }
            if(name.matches("[0-9]*") && test.getValue() == Integer.parseInt(name)) {
                mode = test;
            }
        }
        if(mode == null) {
            player.sendError("That is not a valid game mode.");
        }
        return mode;
    }
}
