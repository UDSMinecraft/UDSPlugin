package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import com.undeadscythes.udsplugin.*;

/**
 * Let's a player ignore other players in public chat.
 * @author UndeadScythes
 */
public class IgnoreCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsEq(1) && (target = getMatchingPlayer(args[0])) != null && notSelf(target)) {
            if(target.getRank().compareTo(PlayerRank.WARDEN) < 0) {
                if(player.ignorePlayer(target)) {
                    player.sendMessage(Color.MESSAGE + "you are now ignoring " + target.getDisplayName() + ".");
                } else {
                    player.unignorePlayer(target);
                    player.sendMessage(Color.MESSAGE + "you are no longer ignoring " + target.getDisplayName() + ".");
                }
            }
        }
    }
}
