package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Get statistics on a player.
 * @author UndeadScythes
 */
public class StatsCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(args.length == 0) {
            sendStats(player);
        } else if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null) {
            sendStats(target);
        }
    }

    private void sendStats(final SaveablePlayer target) {
        player.sendMessage(Color.MESSAGE + target.getNick() + "'s stats:");
        player.sendMessage(Color.TEXT + "Minecraft name: " + target.getName());
        player.sendMessage(Color.TEXT + "Rank: " + target.getRankColor() + target.getRankName());
        player.sendMessage(Color.TEXT + "Clan: " + (target.isInClan() ? target.getClan().getName() : "not in clan"));
        player.sendMessage(Color.TEXT + "Current bounty: " + target.getBounty());
        if(target.isOnline()) {
            player.sendMessage(Color.TEXT + "Current level: " + target.getLevel());
        }
        player.sendMessage(Color.TEXT + "Time logged: " + target.getTimeLogged());
        player.sendMessage(Color.TEXT + "Last seen: " + (target.isOnline() && !target.isHidden() ? "online now in world " + target.getWorld().getName() : target.getLastSeen()));
    }
}
