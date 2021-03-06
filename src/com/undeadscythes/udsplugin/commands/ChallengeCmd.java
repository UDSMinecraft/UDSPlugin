package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * A player challenges another player to a private staked PVP match.
 * 
 * @author UndeadScythes
 */
public class ChallengeCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        int wager;
        if(numArgsHelp(2) && notJailed() && (target = matchOnlinePlayer(arg(0))) != null && notJailed(target) && (wager = getInteger(arg(1))) != -1 && canAfford(wager) && canRequest(target) && notDueling(target) && notSelf(target)) {
            UDSPlugin.addRequest(target.getName(), new Request(player(), RequestType.PVP, wager, target));
            target.sendNormal(player().getNick() + " has challenged you to a duel for " + wager + " credits.");
            target.sendMessage(Message.REQUEST_Y_N);
            player().sendMessage(Message.REQUEST_SENT);
        }
    }
}
