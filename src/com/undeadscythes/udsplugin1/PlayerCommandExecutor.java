package com.undeadscythes.udsplugin1;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A command that is designed to be run by a player.
 * @author UndeadScythes
 */
public abstract class PlayerCommandExecutor implements CommandExecutor {
    ExtendedPlayer player;
    int argsLength;

    /**
     * @inheritDoc
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            player = UDSPlugin.getPlayers().get(sender.getName());
            argsLength = args.length;
            playerExecute(player, args);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks that the command has the correct number of arguments.
     * @param length Number of arguments required.
     * @return <code>true</code> if there are sufficient arguments, <code>false</code> if there are too few or too many.
     */
    public boolean argsLength(int length) {
        if(argsLength == length) {
            return true;
        } else {
            player.sendMessage(Message.WRONG_NUM_ARGS + "");
            return false;
        }
    }

    /**
     * Checks if a player is online.
     * @param target Player to check.
     * @return <code>true</code> if the player is online, <code>false</code> otherwise.
     */
    public boolean isOnline(ExtendedPlayer target) {
        if(target.isOnline()) {
            return true;
        } else {
            player.sendMessage(Message.PLAYER_NOT_ONLINE + "");
            return false;
        }
    }

    /**
     * Matches a partial player name to the first player it finds.
     * @param partial Partial player name.
     * @return The first player matched or <code>null</code> if no players matched.
     */
    public ExtendedPlayer matchesPlayer(String partial) {
        ExtendedPlayer target = UDSPlugin.getOnlinePlayers().get(partial);
        if(target!= null) {
            return target;
        } else {
            for(ExtendedPlayer test : UDSPlugin.getOnlinePlayers().values()) {
                if(test.getDisplayName().equals(partial)) {
                    return test;
                }
            }
            if(target!= null) {
                return target;
            } else {
                target = UDSPlugin.getOnlinePlayers().matchKey(partial);
                if(target != null) {
                    return target;
                } else {
                    for(ExtendedPlayer test : UDSPlugin.getOnlinePlayers().values()) {
                        if(test.getDisplayName().matches(partial)) {
                            return test;
                        }
                    }
                    target = UDSPlugin.getPlayers().get(partial);
                    if(target != null) {
                        return target;
                    } else {
                        for(ExtendedPlayer test : UDSPlugin.getPlayers().values()) {
                            if(test.getDisplayName().equals(partial)) {
                                return test;
                            }
                        }
                        target = UDSPlugin.getPlayers().matchKey(partial);
                        if(target != null) {
                            return target;
                        } else {
                            for(ExtendedPlayer test : UDSPlugin.getPlayers().values()) {
                                if(test.getDisplayName().matches(partial)) {
                                    return test;
                                }
                            }
                        }
                        player.sendMessage(Message.NO_PLAYER + "");
                        return null;
                    }
                }
            }
        }
    }

    /**
     * Matches a warp name from a partial string.
     * @param partial Partial warp name.
     * @return The warp matched or <code>null</code> if there were no matches.
     */
    public Warp matchesWarp(String partial) {
        Warp warp = UDSPlugin.getWarps().get(partial);
        if(warp != null) {
            return warp;
        } else {
            warp = UDSPlugin.getWarps().matchKey(partial);
            if(warp != null) {
                return warp;
            } else {
                player.sendMessage(Message.NO_WARP.toString());
                return null;
            }
        }
    }

    /**
     * Checks if the player can afford a certain cost.
     * @param cost The cost to check.
     * @return <code>true</code> if the player can afford the cost, <code>false</code> otherwise.
     */
    public boolean canAfford(int cost) {
        if(player.canAfford(cost)) {
            return true;
        } else {
            player.sendMessage(Message.CANT_AFFORD + "");
            return false;
        }
    }

    /**
     * Checks if the player has a permission or is opped. The permission is appended to the string "udsplugin.".
     * @param perm The permission suffix.
     * @return <code>true</code> if player has the permission or is opped, <code>false</code> otherwise.
     */
    public boolean hasPerm(String perm) {
        if(player.hasPermission("udsplugin." + perm) || player.isOp()) {
            return true;
        } else {
            player.sendMessage(Message.NO_PERM + "");
            return false;
        }
    }

    /**
     * Used when a player on the server executes a command.
     * @param player Player who ran the command.
     * @param args Arguements of the command.
     */
    public abstract void playerExecute(ExtendedPlayer player, String[] args);
}
