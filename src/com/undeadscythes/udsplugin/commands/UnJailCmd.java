package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Description. Sends help on wrong arguments.
 * @author UndeadScythes
 */
public class UnJailCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null && isJailed(target)) {
            target.release();
            target.sendMessage(Color.MESSAGE + "You have been released early.");
            player.sendMessage(Color.MESSAGE + "You have released " + target.getDisplayName() + ".");
        }
    }
}
