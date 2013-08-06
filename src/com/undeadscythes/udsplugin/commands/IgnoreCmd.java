package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Let's a player ignore other players in public chat.
 * 
 * @author UndeadScythes
 */
public class IgnoreCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = matchPlayer(arg(0))) != null && notSelf(target) && !target.hasPermission(Perm.UNAVOIDABLE)) {
            if(player().ignorePlayer(target)) {
                player().sendNormal("You are now ignoring " + target.getNick() + ".");
            } else {
                player().unignorePlayer(target);
                player().sendNormal("You are no longer ignoring " + target.getNick() + ".");
            }
        }
    }
}
