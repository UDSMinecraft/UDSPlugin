package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;

/**
 * Let's a player ignore other players in public chat.
 * @author UndeadScythes
 */
public class IgnoreCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null && notSelf(target) && target.getRank().compareTo(PlayerRank.WARDEN) < 0) {
            if(player.ignorePlayer(target)) {
                player.sendMessage(Color.MESSAGE + "You are now ignoring " + target.getNick() + ".");
            } else {
                player.unignorePlayer(target);
                player.sendMessage(Color.MESSAGE + "You are no longer ignoring " + target.getNick() + ".");
            }
        }
    }
}
