package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.requests.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class ChallengeCmd extends CommandHandler {
    @Override
    public void playerExecute() throws TargetMatchesSenderException, PlayerNotOnlineException {
        int wager;
        if(numArgsHelp(2)) {
            Member target = matchOtherOnlinePlayer(args[0]);
            if(notJailed() && notJailed(target) && (wager = getInteger(args[1])) != -1 && canAfford(wager) && canRequest(target) && notDueling(target)) {
                UDSPlugin.addRequest(target.getName(), new Request(player, RequestType.PVP, wager, target));
                target.sendNormal(player.getNick() + " has challenged you to a duel for " + wager + " credits.");
                target.sendMessage(Message.REQUEST_Y_N);
                player.sendMessage(Message.REQUEST_SENT);
            }
        }
    }
}
