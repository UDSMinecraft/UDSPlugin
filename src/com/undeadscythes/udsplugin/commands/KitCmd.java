package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.inventory.*;

/**
 * Spawns the player a kit.
 * @author UndeadScythes
 */
public class KitCmd extends CommandValidator {
    @Override
    public final void playerExecute() {
        if(args.length == 0) {
            player.sendNormal("--- Available Kits ---");
            for(Kit kit : UDSPlugin.KITS) {
                String contents = "";
                for(ItemStack item : kit.getItems()) {
                    contents = contents.concat(item.getType().toString().toLowerCase().replace("_", " ") + ", ");
                }
                player.sendListItem(kit.getName() + " (" + kit.getPrice() + "): ", contents.substring(0, contents.length() - 2));
            }
        } else if(numArgsHelp(1)) {
            boolean given = false;
            for(Kit kit : UDSPlugin.KITS) {
                if(kit.getName().equalsIgnoreCase(args[0]) && canAfford(kit.getPrice())) {
                    for(ItemStack item : kit.getItems()) {
                        player.giveAndDrop(item);
                    }
                    player.debit(kit.getPrice());
                    given = true;
                }
            }
            if(!given) {
                player.sendError("No kit found by that name.");
            }
        }
    }
}
