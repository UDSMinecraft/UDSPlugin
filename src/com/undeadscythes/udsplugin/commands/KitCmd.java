package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.inventory.*;

/**
 * Spawns the player a kit.
 * @author UndeadScythes
 */
public class KitCmd extends AbstractPlayerCommand {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        if(args.length == 0) {
            player.sendMessage(Color.MESSAGE + "--- Available Kits ---");
            for(Kit kit : Config.kits) {
                String contents = "";
                for(ItemStack item : kit.getItems()) {
                    contents = contents.concat(item.getType().toString().toLowerCase().replace("_", " ") + ", ");
                }
                player.sendMessage(Color.ITEM + kit.getName() + " (" + kit.getPrice() + "): " + Color.TEXT + contents.substring(0, contents.length() - 2));
            }
        } else if(numArgsHelp(1)) {
            boolean given = false;
            for(Kit kit : Config.kits) {
                if(kit.getName().equalsIgnoreCase(args[0]) && canAfford(kit.getPrice())) {
                    for(ItemStack item : kit.getItems()) {
                        player.giveAndDrop(item);
                    }
                    player.debit(kit.getPrice());
                    given = true;
                }
            }
            if(!given) {
                player.sendMessage(Color.ERROR + "No kit found by that name.");
            }
        }
    }
}
