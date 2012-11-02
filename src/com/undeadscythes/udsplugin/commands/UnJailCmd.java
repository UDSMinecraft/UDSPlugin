package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Description.
 * @author UndeadScythes
 */
public class UnJailCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsEq(1) && (target = matchesPlayer(args[0])) != null && isJailed(target)) {
            target.release();
            target.sendMessage(Color.MESSAGE + "You have been released early.");
            player.sendMessage(Color.MESSAGE + "You have released " + target.getDisplayName() + ".");
        }
    }
}
