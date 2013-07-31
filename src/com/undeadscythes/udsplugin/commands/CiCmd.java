package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Clears all items from a player's inventory.
 * @author UndeadScythes
 */
public class CiCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(args.length == 1) {
            player.getInventory().clear(-1, -1);
            player.sendNormal("Inventory cleared.");
        } else if(maxArgsHelp(2)) {
            final SaveablePlayer target = getMatchingOnlinePlayer(args[0]);
            if(target != null && notSelf(target) && outRanks(target)) {
                target.getInventory().clear(-1, -1);
                player.sendNormal(target.getNick() + "'s inventory was cleared.");
                target.sendMessage("Your inventory has been cleared by a mod.");
            }
        }
    }
}
