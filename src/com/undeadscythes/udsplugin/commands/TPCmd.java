package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleport players to each other.
 * @author UndeadScythes
 */
public class TPCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer targetFrom;
        SaveablePlayer targetTo;
        if(args.length == 1 && (targetTo = getMatchingPlayer(args[0])) != null && isOnline(targetTo) && notSelf(targetTo)) {
            player.setBackPoint();
            player.teleport(targetTo);
        } else if(args.length == 2 && (targetFrom = getMatchingPlayer(args[0])) != null && isOnline(targetFrom) && (targetTo = getMatchingPlayer(args[1])) != null && isOnline(targetTo)) {
            targetFrom.setBackPoint();
            targetFrom.teleport(targetTo);
        }
    }
}
