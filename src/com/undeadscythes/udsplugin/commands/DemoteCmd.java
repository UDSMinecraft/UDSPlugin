package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;

/**
 * Demote a player by a single rank.
 * @author UndeadScythes
 */
public class DemoteCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null && notSelf(target)) {
            PlayerRank rank;
            if(player.getRank().compareTo(target.getRank()) >= 0 && (rank = target.demote()) != null) {
                player.sendMessage(Color.MESSAGE + target.getNick() + " has been demoted to " + rank.toString() + ".");
                target.sendMessage(Color.MESSAGE + "You have been demoted to " + rank.toString() + ".");
            } else {
                player.sendMessage(Color.ERROR + "You can't demote this player any further.");
            }
        }
    }
}
