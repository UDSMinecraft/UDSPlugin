package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.Color;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;

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
        ExtendedPlayer target;
        if(hasPerm("god")) {
            if(args.length == 0) {
                player.sendMessage(Color.MESSAGE + "You now have god mode " + (player.toggleGodMode() ? "en" : "dis") + "abled.");
            } else if(args.length == 1 && (target = matchesPlayer(args[0])) != null) {
                boolean godMode = target.toggleGodMode();
                player.sendMessage(Color.MESSAGE + target.getDisplayName() + " now has god mode " + (godMode ? "en" : "dis") + "abled.");
                target.sendMessage(Color.MESSAGE + "You now have god mode " + (godMode ? "en" : "dis") + "abled.");
            }
        }
    }

}
