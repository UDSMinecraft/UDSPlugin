package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Remove a ban on a player.
 * @author UndeadScythes
 */
public class UnBanCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null && isBanned(target) && notSelf(target)) {
            target.setBanned(false);
            player.sendNormal(target.getNick() + " is no longer banned.");
        }
    }
}
