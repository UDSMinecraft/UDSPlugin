package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Checks the identity of a player.
 * @author UndeadScythes
 */
public class WhoIsCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = matchesPlayer(arg(0))) != null) {
            player().sendNormal(target.getNick() + " is " + target.getName() + ".");
        }
    }
}
