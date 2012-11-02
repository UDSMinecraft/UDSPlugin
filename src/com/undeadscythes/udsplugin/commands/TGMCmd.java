package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Toggle a players game mode.
 * @author UndeadScythes
 */
public class TGMCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsLessEq(1)) {
            SaveablePlayer target;
            if(args.length == 0) {
                player.sendMessage(Color.MESSAGE + "You now have creative mode " + (player.toggleGameMode() ? "en" : "dis") + "abled.");
            } else if(args.length == 1 && (target = matchesPlayer(args[0])) != null) {
                boolean gameMode = target.toggleGameMode();
                if(!player.equals(target)) {
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " now has creative mode " + (gameMode ? "en" : "dis") + "abled.");
                }
                target.sendMessage(Color.MESSAGE + "You now have creative mode " + (gameMode ? "en" : "dis") + "abled.");
            }
        }
    }
}
