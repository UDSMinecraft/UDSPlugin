package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsplugin.*;
import org.bukkit.inventory.*;

/**
 * @author UndeadScythes
 */
public class ICmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(minArgsHelp(1) && maxArgsHelp(2)) {
            ItemStack item;
            if((item = getItem(args[0])) != null) {
                int amount;
                if(args.length == 1) {
                    item.setAmount(item.getMaxStackSize());
                } else if((amount = getInteger(args[1])) != -1) {
                    item.setAmount(amount);
                }
                if(player().hasPerm(Perm.I_ADMIN)) {
                    player().getInventory().addItem(item);
                } else if(player().getVIPSpawns() > 0) {
                    if(Config.VIP_WHITELIST.contains(item.getType())) {
                        if(item.getAmount() > player().getVIPSpawns()) {
                            item.setAmount(player().getVIPSpawns());
                        }
                        try {
                            if(player().useVIPSpawns(item.getAmount()) == 0) {
                                player().sendNormal("You have just used up your last spawns for today.");
                            }
                        } catch (NoMetadataSetException ex) {}
                        player().getInventory().addItem(item);
                    } else {
                        player().sendError("Sorry, " + item.getType().name().toLowerCase().replace("_", " ") + " is not a whitelisted item.");
                    }
                } else {
                    player().sendError("You are out of spawns for today.");
                }
            }
        }
    }
}
