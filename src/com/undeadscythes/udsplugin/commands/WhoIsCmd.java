package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Description.
 * @author UndeadScythes
 */
public class WhoIsCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsEq(1) && (target = getMatchingPlayer(args[0])) != null) {
            player.sendMessage(Color.MESSAGE + target.getDisplayName() + " is " + target.getName() + ".");
        }
    }
}
