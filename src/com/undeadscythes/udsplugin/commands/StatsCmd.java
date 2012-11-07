package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Get statistics on a player.
 * @author UndeadScythes
 */
public class StatsCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsEq(1) && (target = getMatchingPlayer(args[0])) != null) {
            player.sendMessage(Color.MESSAGE + target.getDisplayName() + "'s stats:");
            player.sendMessage(Color.TEXT + "Minecraft name: " + target.getName());
            player.sendMessage(Color.TEXT + "Rank: " + target.getRank().color() + target.getRank().toString());
            player.sendMessage(Color.TEXT + "Clan: " + (target.isInClan() ? target.getClan() : "not in clan"));
            player.sendMessage(Color.TEXT + "Current bounty: " + target.getBounty());
            player.sendMessage(Color.TEXT + "Current level: " + target.getLevel());
            player.sendMessage(Color.TEXT + "Time logged: " + player.getTimeLogged());
            player.sendMessage(Color.TEXT + "Last seen: " + (player.isOnline() ? "online now" : player.getLastSeen()));
        }
    }
}
