package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Remove a ban on a player.
 * 
 * @author UndeadScythes
 */
public class UnBanCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = matchPlayer(arg(0))) != null && isBanned(target) && notSelf(target)) {
            target.setBanned(false);
            player().sendNormal(target.getNick() + " is no longer banned.");
        }
    }
}
