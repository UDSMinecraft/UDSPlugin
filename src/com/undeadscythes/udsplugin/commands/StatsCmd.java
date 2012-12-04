package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Get statistics on a player.
 * @author UndeadScythes
 */
public class StatsCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null) {
            player.sendMessage(Color.MESSAGE + target.getNick() + "'s stats:");
            player.sendMessage(Color.TEXT + "Minecraft name: " + target.getName());
            player.sendMessage(Color.TEXT + "Rank: " + target.getRank().getColor() + target.getRank().toString());
            player.sendMessage(Color.TEXT + "Clan: " + (target.isInClan() ? target.getClan() : "not in clan"));
            player.sendMessage(Color.TEXT + "Current bounty: " + target.getBounty());
            player.sendMessage(Color.TEXT + "Current level: " + target.getLevel());
            player.sendMessage(Color.TEXT + "Time logged: " + player.getTimeLogged());
            player.sendMessage(Color.TEXT + "Last seen: " + (player.isOnline() ? "online now" : player.getLastSeen()));
        }
    }
}
