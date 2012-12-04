package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Toggle a players god mode.
 * @author UndeadScythes
 */
public class GodCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(args.length == 0) {
            player.sendMessage(Color.MESSAGE + "You now have god mode " + (player.toggleGodMode() ? "en" : "dis") + "abled.");
        } else if(numArgsHelp(1) && (target = getMatchingOtherPlayer(args[0])) != null) {
            player.sendMessage(Color.MESSAGE + target.getNick() + " now has god mode " + (target.toggleGodMode() ? "en" : "dis") + "abled.");
            target.sendMessage(Color.MESSAGE + "You now have god mode " + (target.hasGodMode() ? "en" : "dis") + "abled.");
        }
    }

}
