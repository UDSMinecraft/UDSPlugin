package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class PromoteCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        if(numArgsHelp(1) && (target = matchOnlinePlayer(args[0])) != null && notSelf(target)) {
            PlayerRank rank;
            if(player().outRanks(target) && (rank = target.promote()) != null) {
                player().sendNormal(target.getNick() + " has been promoted to " + rank.toString() + ".");
                target.sendNormal("You have been promoted to " + rank.toString() + ".");
            } else {
                player().sendError("You can't promote this player any further.");
            }
        }
    }
}
