package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Remove a player from jail.
 * @author UndeadScythes
 */
public class UnJailCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null && isJailed(target)) {
            target.release();
            target.sendMessage(Color.MESSAGE + "You have been released early.");
            player.sendMessage(Color.MESSAGE + "You have released " + target.getNick() + ".");
        }
    }
}
