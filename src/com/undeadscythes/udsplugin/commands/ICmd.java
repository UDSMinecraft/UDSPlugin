package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.inventory.*;

/**
 * Spawn items into the players inventory.
 * @author UndeadScythes
 */
public class ICmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(minArgsHelp(1) && maxArgsHelp(2)) {
            ItemStack item;
            if((item = itemExists(arg(0))) != null) {
                int amount;
                if(argsLength() == 1) {
                    item.setAmount(item.getMaxStackSize());
                } else if((amount = isInteger(arg(1))) != -1) {
                    item.setAmount(amount);
                }
                if(player().hasPermission(Perm.I_ADMIN)) {
                    player().getInventory().addItem(item);
                } else if(player().getVIPSpawns() > 0) {
                    if(Config.VIP_WHITELIST.contains(item.getType())) {
                        if(item.getAmount() > player().getVIPSpawns()) {
                            item.setAmount(player().getVIPSpawns());
                        }
                        if(player().useVIPSpawns(item.getAmount()) == 0) {
                            player().sendNormal("You have just used up your last spawns for today.");
                        }
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
