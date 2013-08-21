package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * Remove a ban on a player.
 * 
 * @author UndeadScythes
 */
public class UnBanCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        Member target;
        if(numArgsHelp(1) && (target = matchPlayer(args[0])) != null && isBanned(target) && notSelf(target)) {
            target.setBanned(false);
            player().sendNormal(target.getNick() + " is no longer banned.");
        }
    }
}
