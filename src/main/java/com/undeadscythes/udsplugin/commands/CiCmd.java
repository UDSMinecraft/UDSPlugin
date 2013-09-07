package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class CiCmd extends CommandHandler {
    @Override
    public void playerExecute() throws TargetMatchesSenderException, PlayerNotOnlineException {
        if(args.length == 0) {
            player.getInventory().clear(-1, -1);
            player.sendNormal("Inventory cleared.");
        } else if(maxArgsHelp(1)) {
            final Member target = matchOtherOnlinePlayer(args[0]);
            if(outRanks(target.getOfflineMember())) {
                target.getInventory().clear(-1, -1);
                player.sendNormal(target.getNick() + "'s inventory was cleared.");
                target.sendMessage("Your inventory has been cleared by a mod.");
            }
        }
    }
}
