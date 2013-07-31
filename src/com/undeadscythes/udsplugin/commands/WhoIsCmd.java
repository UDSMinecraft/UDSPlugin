package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Checks the identity of a player.
 * @author UndeadScythes
 */
public class WhoIsCmd extends CommandValidator {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null) {
            player.sendNormal(target.getNick() + " is " + target.getName() + ".");
        }
    }
}
