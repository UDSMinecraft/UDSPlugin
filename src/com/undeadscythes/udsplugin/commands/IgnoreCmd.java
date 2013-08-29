package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * Let's a player ignore other players in public chat.
 *
 * @author UndeadScythes
 */
public class IgnoreCmd extends CommandHandler {
    @Override
    public void playerExecute() throws TargetMatchesSenderException, NoPlayerFoundException {
        if(numArgsHelp(1)) {
            OfflineMember target = matchOtherPlayer(args[0]);
            if(!target.hasPerm(Perm.UNAVOIDABLE)) {
                if(player.ignorePlayer(target)) {
                    player.sendNormal("You are now ignoring " + target.getNick() + ".");
                } else {
                    player.unignorePlayer(target);
                    player.sendNormal("You are no longer ignoring " + target.getNick() + ".");
                }
            }
        }
    }
}
