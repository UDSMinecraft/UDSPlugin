package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Get a list of special signs the player can place.
 * @author UndeadScythes
 */
public class SignsCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        player.sendMessage(Color.MESSAGE + "Special signs available to you and format (lines 1-4):");
        if(player.hasPermission("udsplugin.sign.checkpoint")) {
            player.sendMessage(Color.ITEM + "Checkpoint - " + Color.TEXT + "1: [checkpoint], 2-4: Anything");
        }
        if(player.hasPermission("udsplugin.sign.minecart")) {
            player.sendMessage(Color.ITEM + "Minecart - " + Color.TEXT + "1: [minecart], 2-4: Anything");
        }
        if(player.hasPermission("udsplugin.sign.item")) {
            player.sendMessage(Color.ITEM + "Item - " + Color.TEXT + "1: [item], 2: <item>, 3: <amount>, 4: Anything");
        }
        if(player.hasPermission("udsplugin.sign.prize")) {
            player.sendMessage(Color.ITEM + "Prize - " + Color.TEXT + "1: [prize], 2: <item>, 3: <amount>, 4: Anything");
        }
        if(player.hasPermission("udsplugin.sign.warp")) {
            player.sendMessage(Color.ITEM + "Warp - " + Color.TEXT + "1: [warp], 2: <warp>, 3-4: Anything");
        }
        if(player.hasPermission("udsplugin.sign.spleef")) {
            player.sendMessage(Color.ITEM + "Spleef - " + Color.TEXT + "1: [spleef], 2: <region>, 3-4: Anything");
        }
    }
}
