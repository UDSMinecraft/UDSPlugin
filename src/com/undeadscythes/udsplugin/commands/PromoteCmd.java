package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Promote a player by a single rank.
 * @author UndeadScythes
 */
public class PromoteCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null && notSelf(target)) {
            PlayerRank rank;
            if(player.outRanks(target) && (rank = target.promote()) != null) {
                player.sendNormal(target.getNick() + " has been promoted to " + rank.toString() + ".");
                target.sendNormal("You have been promoted to " + rank.toString() + ".");
            } else {
                player.sendError("You can't promote this player any further.");
            }
        }
    }
}
