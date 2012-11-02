package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.ExtendedPlayer.Rank;
import com.undeadscythes.udsplugin.*;

/**
 * Promote a player by a single rank.
 * @author UndeadScythes
 */
public class PromoteCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        ExtendedPlayer target;
        if(argsEq(1) && (target = matchesPlayer(args[0])) != null && notSelf(target)) {
            Rank rank;
            if(player.getRank().compareTo(target.getRank()) > 0 && (rank = target.promote()) != null) {
                player.sendMessage(Color.MESSAGE + target.getDisplayName() + " has been promoted to " + rank.toString() + ".");
                target.sendMessage(Color.MESSAGE + "You have been promoted to " + rank.toString() + ".");
            } else {
                player.sendMessage(Message.CANT_PROMOTE);
            }
        }
    }
}
