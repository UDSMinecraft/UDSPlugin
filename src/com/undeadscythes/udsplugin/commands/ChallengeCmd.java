package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * A player challenges another player to a private staked PVP match.
 * @author UndeadScythes
 */
public class ChallengeCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        int wager;
        if(numArgsHelp(2) && notJailed() && (target = getMatchingPlayer(args[0])) != null && isOnline(target) && notJailed(target) && (wager = parseInt(args[1])) != -1 && canAfford(wager) && noRequests(target) && notDueling(target) && notSelf(target)) {
            UDSPlugin.getRequests().put(target.getName(), new Request(player, Request.RequestType.PVP, wager, target));
            target.sendMessage(Color.MESSAGE + player.getNick() + " has challenged you to a duel for " + wager + " credits.");
            target.sendMessage(Message.REQUEST_Y_N);
            player.sendMessage(Message.REQUEST_SENT);
        }
    }
}
