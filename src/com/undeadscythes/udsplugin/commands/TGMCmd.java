package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Toggle a players game mode.
 * @author UndeadScythes
 */
public class TGMCmd extends CommandValidator {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(args.length == 0) {
            player.sendNormal("You now have creative mode " + (player.toggleGameMode() ? "en" : "dis") + "abled.");
        } else if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null) {
            final boolean gameMode = target.toggleGameMode();
            if(!player.equals(target)) {
                player.sendNormal(target.getNick() + " now has creative mode " + (gameMode ? "en" : "dis") + "abled.");
            }
            target.sendNormal("You now have creative mode " + (gameMode ? "en" : "dis") + "abled.");
        }
    }
}
