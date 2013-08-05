package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Get statistics on a player.
 * @author UndeadScythes
 */
public class StatsCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(argsLength() == 0) {
            sendStats(player());
        } else if(numArgsHelp(1) && (target = matchesPlayer(arg(0))) != null) {
            sendStats(target);
        }
    }

    private void sendStats(final SaveablePlayer target) {
        player().sendNormal(target.getNick() + "'s stats:");
        player().sendText("Minecraft name: " + target.getName());
        player().sendText("Rank: " + target.getRankColor() + target.getRankName());
        player().sendText("Clan: " + (target.isInClan() ? target.getClan().getName() : "not in clan"));
        player().sendText("Current bounty: " + target.getBounty());
        if(target.isOnline()) {
            player().sendText("Current level: " + target.getLevel());
        }
        player().sendText("Time logged: " + target.getTimeLogged());
        player().sendText("Last seen: " + (target.isOnline() && !target.isHidden() ? "online now in world " + target.getWorld().getName() : target.getLastSeen()));
    }
}
