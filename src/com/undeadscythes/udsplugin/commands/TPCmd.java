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
    public void playerExecute(ExtendedPlayer player, String[] args) {
        ExtendedPlayer targetFrom;
        ExtendedPlayer targetTo;
        if(argsMoreLessInc(1, 2)) {
            if(args.length == 1 && (targetTo = matchesPlayer(args[0])) != null && isOnline(targetTo) && notSelf(targetTo)) {
                player.setBackPoint();
                player.teleport(targetTo);
            } else if((targetFrom = matchesPlayer(args[0])) != null && isOnline(targetFrom) && (targetTo = matchesPlayer(args[1])) != null && isOnline(targetTo)) {
                targetFrom.setBackPoint();
                targetFrom.teleport(targetTo);
            }
        }
    }
}
