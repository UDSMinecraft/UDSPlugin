package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class PromoteCmd extends CommandHandler {
    private OfflineMember target;

    @Override
    public boolean executeCheck() throws TargetMatchesSenderException, NoPlayerFoundException {
        if(numArgsHelp(1)) {
            target = matchOtherPlayer(args[0]);
            return true;
        }
        return false;
    }

    @Override
    public void playerExecute() {
        if(player.outRanks(target)) promote();
    }

    @Override
    public void consoleExecute() {
        promote();
    }

    private void promote() {
        MemberRank rank;
        if((rank = target.promote()) != null) {
            sender.sendNormal(target.getNick() + " has been promoted to " + rank.toString() + ".");
            target.sendNormal("You have been promoted to " + rank.toString() + ".");
        } else {
            sender.sendError("You can't promote this player any further.");
        }
        MemberUtils.saveMember(target);
    }
}
