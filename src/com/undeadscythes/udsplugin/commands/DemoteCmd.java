package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Demote a player by a single rank.
 * @author UndeadScythes
 */
public class DemoteCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null && notSelf(target)) {
            PlayerRank rank;
            if(player.sameRank(target) && (rank = target.demote()) != null) {
                player.sendNormal(target.getNick() + " has been demoted to " + rank.toString() + ".");
                target.sendNormal("You have been demoted to " + rank.toString() + ".");
            } else {
                player.sendError("You can't demote this player any further.");
            }
        }
    }
}
