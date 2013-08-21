package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class ChallengeCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        int wager;
        if(numArgsHelp(2) && notJailed() && (target = matchOnlinePlayer(args[0])) != null && notJailed(target) && (wager = getInteger(args[1])) != -1 && canAfford(wager) && canRequest(target) && notDueling(target) && notSelf(target)) {
            UDSPlugin.addRequest(target.getName(), new Request(player(), RequestType.PVP, wager, target));
            target.sendNormal(player().getNick() + " has challenged you to a duel for " + wager + " credits.");
            target.sendMessage(Message.REQUEST_Y_N);
            player().sendMessage(Message.REQUEST_SENT);
        }
    }
}
