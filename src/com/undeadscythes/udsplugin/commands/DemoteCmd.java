package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class DemoteCmd extends CommandHandler {
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
        if(player.outRanks(target)) demote();
    }

    @Override
    public void consoleExecute() {
        demote();
    }

    private void demote() {
        final MemberRank rank;
        if((rank = target.demote()) != null) {
            sender.sendNormal(target.getNick() + " has been demoted to " + rank.toString() + ".");
            try {
                MemberUtils.getOnlineMember(target).sendNormal("You have been demoted to " + rank.toString() + ".");
            } catch (PlayerNotOnlineException ex) {}
        } else {
            sender.sendError("You can't demote this player any further.");
        }
        MemberUtils.saveMember(target);
    }
}
