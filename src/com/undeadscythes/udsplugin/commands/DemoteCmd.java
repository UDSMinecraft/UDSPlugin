package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.SaveablePlayer.Rank;
import com.undeadscythes.udsplugin.*;

/**
 * Demote a player by a single rank.
 * @author UndeadScythes
 */
public class DemoteCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsEq(1) && (target = matchesPlayer(args[0])) != null && notSelf(target)) {
            Rank rank;
            if((rank = target.demote()) != null) {
                player.sendMessage(Color.MESSAGE + target.getDisplayName() + " has been demoted to " + rank.toString() + ".");
                target.sendMessage(Color.MESSAGE + "You have been demoted to " + rank.toString() + ".");
            } else {
                player.sendMessage(Color.ERROR + "You can't demote this player any further.");
            }
        }
    }
}
