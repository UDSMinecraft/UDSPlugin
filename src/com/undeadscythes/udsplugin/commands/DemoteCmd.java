package com.undeadscythes.udsplugin.commands;

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
    public void playerExecute(ExtendedPlayer player, String[] args) {
        ExtendedPlayer target;
        if(argsEq(1) && (target = matchesPlayer(args[0])) != null && notSelf(target)) {
            ExtendedPlayer.Rank rank;
            if((rank = target.demote()) != null) {
                player.sendMessage(Color.MESSAGE + target.getDisplayName() + " has been demoted to " + rank.toString() + ".");
                target.sendMessage(Color.MESSAGE + "You have been demoted to " + rank.toString() + ".");
            } else {
                player.sendMessage(Message.CANT_DEMOTE);
            }
        }
    }
}
