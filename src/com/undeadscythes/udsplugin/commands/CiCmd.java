package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class CiCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(args.length == 0) {
            player().getInventory().clear(-1, -1);
            player().sendNormal("Inventory cleared.");
        } else if(maxArgsHelp(1)) {
            final Member target = matchOnlinePlayer(args[0]);
            if(target != null && notSelf(target) && outRanks(target)) {
                target.getInventory().clear(-1, -1);
                player().sendNormal(target.getNick() + "'s inventory was cleared.");
                target.sendMessage("Your inventory has been cleared by a mod.");
            }
        }
    }
}
