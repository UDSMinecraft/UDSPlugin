package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

/**
 * Spawns the player a kit.
 * @author UndeadScythes
 */
public class KitCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsMoreLessInc(0, 1)) {
            if(args.length == 0) {
                player.sendMessage(Color.MESSAGE + "--- Available Kits ---");
                for(String kit : Config.KITS) {
                    String[] kitSplit = kit.split(",");
                    String contents = "";
                    for(String item : StringUtils.split(kit.replace(kitSplit[0] + "," + kitSplit[1] + ",", ""), ",")) {
                        contents = contents.concat(Material.getMaterial(Integer.parseInt(item)).name().toLowerCase().replace("_", " ") + ", ");
                    }
                    player.sendMessage(Color.ITEM + kitSplit[0] + " (" + Integer.parseInt(kitSplit[1]) + "): " + Color.TEXT + contents.substring(0, contents.length() - 2));
                }
            } else {
                boolean given = false;
                for(String kit : Config.KITS) {
                    String[] kitSplit = kit.split(",");
                    if(kitSplit[0].equalsIgnoreCase(args[0]) && canAfford(Integer.parseInt(kitSplit[1]))) {
                        for(String item : StringUtils.split(kit.replace(kitSplit[0] + "," + kitSplit[1] + ",", ""), ",")) {
                            player.giveAndDrop(new ItemStack(Material.getMaterial(Integer.parseInt(item))));
                        }
                        player.debit(Integer.parseInt(kitSplit[1]));
                        given = true;
                    }
                }
                if(!given) {
                    player.sendMessage(Color.ERROR + "No kit found by that name.");
                }
            }
        }
    }
}
