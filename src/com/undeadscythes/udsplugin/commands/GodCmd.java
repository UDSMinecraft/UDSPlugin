package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class GodCmd extends CommandHandler {
    @Override
    public void playerExecute() throws TargetMatchesSenderException, PlayerNotOnlineException {
        Member target;
        if(args.length == 0) {
            player.sendNormal("You now have god mode " + (player.toggleGodMode() ? "en" : "dis") + "abled.");
        } else if(numArgsHelp(1) && (target = matchOtherOnlinePlayer(args[0])) != null) {
            player.sendNormal(target.getNick() + " now has god mode " + (target.toggleGodMode() ? "en" : "dis") + "abled.");
            target.sendNormal("You now have god mode " + (target.hasGodMode() ? "en" : "dis") + "abled.");
        }
    }

}
