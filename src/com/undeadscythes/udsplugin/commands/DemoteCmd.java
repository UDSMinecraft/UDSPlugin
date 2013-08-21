package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * @author UndeadScythes
 */
public class DemoteCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        final Member target;
        if(numArgsHelp(1) && (target = matchPlayer(args[0])) != null && notSelf(target)) {
            final PlayerRank rank;
            if(player().outRanks(target) && (rank = target.demote()) != null) {
                player().sendNormal(target.getNick() + " has been demoted to " + rank.toString() + ".");
                try {
                    PlayerUtils.getOnlinePlayer(target).sendNormal("You have been demoted to " + rank.toString() + ".");
                } catch (PlayerNotOnlineException ex) {}
            } else {
                player().sendError("You can't demote this player any further.");
            }
        }
    }
}
