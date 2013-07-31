package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * A player challenges another player to a private staked PVP match.
 * @author UndeadScythes
 */
public class ChallengeCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        int wager;
        if(numArgsHelp(2) && notJailed() && (target = getMatchingPlayer(args[0])) != null && isOnline(target) && notJailed(target) && (wager = parseInt(args[1])) != -1 && canAfford(wager) && canRequest(target) && notDueling(target) && notSelf(target)) {
            UDSPlugin.addRequest(target.getName(), new Request(player, RequestType.PVP, wager, target));
            target.sendNormal(player.getNick() + " has challenged you to a duel for " + wager + " credits.");
            target.sendMessage(Message.REQUEST_Y_N);
            player.sendMessage(Message.REQUEST_SENT);
        }
    }
}
