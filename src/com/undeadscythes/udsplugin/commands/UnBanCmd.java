package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Remove a ban on a player.
 * @author UndeadScythes
 */
public class UnBanCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsEq(1) && (target = matchesPlayer(args[0])) != null && isBanned(target) && notSelf(target)) {
            target.setBanned(false);
            player.sendMessage(Color.MESSAGE + target.getDisplayName() + " is no longer banned.");
        }
    }
}
