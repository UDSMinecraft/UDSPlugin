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
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsLessEq(1)) {
            SaveablePlayer target;
            if(args.length == 0) {
                player.sendMessage(Color.MESSAGE + "You now have god mode " + (player.toggleGodMode() ? "en" : "dis") + "abled.");
            } else if((target = getMatchingOtherPlayer(args[0])) != null) {
                player.sendMessage(Color.MESSAGE + target.getDisplayName() + " now has god mode " + (target.toggleGodMode() ? "en" : "dis") + "abled.");
                target.sendMessage(Color.MESSAGE + "You now have god mode " + (target.hasGodMode() ? "en" : "dis") + "abled.");
            }
        }
    }

}
