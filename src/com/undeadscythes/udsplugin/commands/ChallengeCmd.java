package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * A player challenges another player to a private staked PVP match.
 * @author UndeadScythes
 */
public class ChallengeCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        int wager;
        if(notJailed() && argsEq(2) && (target = matchesPlayer(args[0])) != null && isOnline(target) && notJailed(target) && (wager = parseInt(args[1])) != -1 && canAfford(wager) && notBusy(target) && notDueling(target) && notSelf(target)) {
            UDSPlugin.getRequests().put(target.getName(), new Request(player, Request.Type.PVP, wager, target));
            target.sendMessage(Color.MESSAGE + player.getDisplayName() + " has challenged you to a duel for " + wager + " credits.");
            target.sendMessage(Message.REQUEST_Y_N);
            player.sendMessage(Message.REQUEST_SENT);
        }
    }
}
