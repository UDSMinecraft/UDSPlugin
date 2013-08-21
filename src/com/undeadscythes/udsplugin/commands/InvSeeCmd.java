package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class InvSeeCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        if(args.length == 0) {
            if(player().getInventoryCopy() == null) {
                player().sendError("You have no saved inventory.");
            } else {
                player().loadInventory();
                player().loadArmor();
                player().sendNormal("Your inventory has been restored.");
            }
        } else if(numArgsHelp(1) && (target = matchOnlinePlayer(args[0])) != null && notSelf(target)) {
            if(player().getInventoryCopy() == null) {
                player().saveInventory();
                player().saveArmor();
                player().getInventory().setContents(target.getInventory().getContents());
                player().getInventory().setArmorContents(target.getInventory().getArmorContents());
            } else {
                player().getInventory().setContents(target.getInventory().getContents());
                player().getInventory().setArmorContents(target.getInventory().getArmorContents());
            }
            player().sendNormal("You now have a copy of " + target.getNick() + "'s inventory.");
        }
    }
}
