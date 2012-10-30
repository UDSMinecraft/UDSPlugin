package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Toggle a players god mode.
 * @author UndeadScythes
 */
public class GodCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(argsLessEq(1)) {
            ExtendedPlayer target;
            if(args.length == 0) {
                player.sendMessage(Color.MESSAGE + "You now have god mode " + (player.toggleGodMode() ? "en" : "dis") + "abled.");
            } else if(args.length == 1 && (target = matchesPlayer(args[0])) != null) {
                boolean godMode = target.toggleGodMode();
                if(!player.equals(target)) {
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " now has god mode " + (godMode ? "en" : "dis") + "abled.");
                }
                target.sendMessage(Color.MESSAGE + "You now have god mode " + (godMode ? "en" : "dis") + "abled.");
            }
        }
    }

}
