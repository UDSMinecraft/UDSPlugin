package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Remove a player from jail.
 * @author UndeadScythes
 */
public class UnJailCmd extends CommandValidator {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null && isJailed(target)) {
            target.release();
            target.sendNormal("You have been released early.");
            player.sendNormal("You have released " + target.getNick() + ".");
        }
    }
}
