package com.undeadscythes.udsplugin;

import org.apache.commons.lang.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

/**
 * A command that is designed to be run by a player.
 * @author UndeadScythes
 */
public abstract class PlayerCommandExecutor implements CommandExecutor {
    private ExtendedPlayer player;
    private int argsLength;

    /**
     * @inheritDoc
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        player = UDSPlugin.getPlayers().get(sender.getName());
        if(sender instanceof Player && hasPerm(Perm.valueOf(command.getName().toUpperCase()))) {
            argsLength = args.length;
            playerExecute(player, args);
            return true;
        } else {
            return false;
        }
    }

    public Rank matchesRank(String string) {
        Rank rank = Rank.valueOf(string);
        if(rank != null) {
            return rank;
        } else {
            player.sendMessage(Message.NOT_A_RANK);
            return null;
        }
    }

    /**
     * Check if a string has bad words in it.
     * @param string String to check.
     * @return <code>true</code> if the word was clean, <code>false</code> otherwise.
     */
    public boolean censor(String string) {
        String[] badWords = new String[]{"fuvg", "shpx", "phag", "avttre"};
        for(String word : badWords) {
            if(string.toLowerCase().contains(rot13(word))) {
                player.sendMessage(Message.CANT_USE_BAD_WORDS);
                return false;
            }
        }
        return true;
    }

    /**
     * This let's me hide the nasty words in the previous function. Interesting fact: "FU" maps to "SH". It took me a few minutes trying to figure out why the first two letters weren't being mapped... but they were!
     * @param string String to rot.
     * @return Rotted string.
     */
    private String rot13(String string) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyzabcdefghijklm".toCharArray();
        String rotted = "";
        for(int i = 0 ; i < string.length(); i++) {
            rotted = rotted.concat(CharUtils.toString(alphabet[ArrayUtils.indexOf(alphabet, string.charAt(i)) + 13]));
        }
        return rotted;
    }

    /**
     * Check that the player is not targeting themselves.
     * @param target Target to check.
     * @return <code>true</code> if target and this player are distinct, <code>false</code> otherwise.
     */
    public boolean notSelf(ExtendedPlayer target) {
        if(!target.equals(player)) {
            return true;
        } else {
            player.sendMessage(Message.NOT_SELF);
            return false;
        }
    }

    /**
     * Check if a player is in clan.
     * @return <code>true</code> if player is in a clan, <code>false</code> otherwise.
     */
    public boolean isInClan() {
        if(player.isInClan()) {
            return true;
        } else {
            player.sendMessage(Message.NOT_IN_CLAN);
            return false;
        }
    }

    /**
     * Check that the player is not pinned due to attacking another player recently.
     * @return <code>true</code> if the player is not pinned, <code>false</code> otherwise.
     */
    public boolean notPinned() {
        if(player.getLastDamageCaused() + Config.PVP_TIME < System.currentTimeMillis()) {
            return true;
        } else {
            player.sendMessage(Message.CANT_DO_THAT_NOW);
            return false;
        }
    }

    /**
     * Check that this player is not in jail.
     * @return <code>true</code> if the player is not in jail, <code>false</code> otherwise.
     */
    public boolean notJailed() {
        if(!player.isJailed()) {
            return true;
        } else {
            player.sendMessage(Message.NOT_WHILE_IN_JAIL);
            return false;
        }
    }

    /**
     * Check that the target player is not in jail.
     * @return <code>true</code> if the target player is not in jail, <code>false</code> otherwise.
     */
    public boolean notJailed(ExtendedPlayer target) {
        if(!target.isJailed()) {
            return true;
        } else {
            player.sendMessage(Message.PLAYER_IN_JAIL);
            return false;
        }
    }

    /**
     * Check that the target player has no requests pending.
     * @param target The player to check.
     * @return <code>true</code> if the target player has no requests pending, <code>false</code> otherwise.
     */
    public boolean notBusy(ExtendedPlayer target) {
        if(!UDSPlugin.getRequests().containsKey(target.getName())) {
            return true;
        } else {
            player.sendMessage(Message.PLAYER_HAS_REQUEST);
            return false;
        }
    }

    /**
     * Check that the target player is not being ignored by the command sender.
     * @param target Player to check.
     * @return <code>true</code> if the target player is not ignoring the command sender, <code>false</code> otherwise.
     */
    public boolean notIgnored(ExtendedPlayer target) {
        if(!target.isIgnoringPlayer(player)) {
            return true;
        } else {
            player.sendMessage(Message.PLAYER_CANT_BE_REACHED);
            return false;
        }
    }

    /**
     * Checks that the command has the correct number of arguments.
     * @param length Number of arguments required.
     * @return <code>true</code> if there are sufficient arguments, <code>false</code> if there are too few or too many.
     */
    public boolean argsEq(int length) {
        if(argsLength == length) {
            return true;
        } else {
            player.sendMessage(Message.WRONG_NUM_ARGS);
            return false;
        }
    }

    /**
     * Checks that the command has the correct number of arguments.
     * @param max Maximum number of arguments.
     * @return <code>true</code> if there aren't too many arguments, <code>false</code> otherwise.
     */
    public boolean argsLessEq(int max) {
        if(argsLength <= max) {
            return true;
        } else {
            player.sendMessage(Message.WRONG_NUM_ARGS);
            return false;
        }
    }

    /**
     * Checks that the command has the correct number of arguments.
     * @param min Minimum number of arguments.
     * @return <code>true</code> if there aren't enough arguments, <code>false</code> otherwise.
     */
    public boolean argsMoreEq(int min) {
        if(argsLength >= min) {
            return true;
        } else {
            player.sendMessage(Message.WRONG_NUM_ARGS);
            return false;
        }
    }

    /**
     * Checks that the command has the correct number of arguments.
     * @param min Minimum number of arguments.
     * @param max Maximum number of arguments.
     * @return <code>true</code> if there the right number of arguments, <code>false</code> otherwise.
     */
    public boolean argsMoreLessInc(int min, int max) {
        if(argsLength >= min && argsLength <= max) {
            return true;
        } else {
            player.sendMessage(Message.WRONG_NUM_ARGS);
            return false;
        }
    }

    /**
     * Checks to see if a string is a positive number.
     * @param number String to check.
     * @return The number if it was one, -1 otherwise.
     */
    public int parseInt(String number) {
        if(number.matches("[0-9][0-9]*")) {
            return Integer.parseInt(number);
        } else {
            player.sendMessage(Message.BAD_NUMBER);
            return -1;
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
            player.sendMessage(Message.PLAYER_NOT_ONLINE);
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
                        player.sendMessage(Message.NO_PLAYER);
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
                player.sendMessage(Message.NO_WARP);
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
            player.sendMessage(Message.CANT_AFFORD);
            return false;
        }
    }

    /**
     * Checks if the player has a permission or is opped. The permission is appended to the string "udsplugin.".
     * @param perm The permission suffix.
     * @return <code>true</code> if player has the permission or is opped, <code>false</code> otherwise.
     */
    public boolean hasPerm(Perm perm) {
        if(player.hasPermission(perm) || player.isOp()) {
            return true;
        } else {
            player.sendMessage(Message.NO_PERM);
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
