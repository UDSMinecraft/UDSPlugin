package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Get a list of special signs the player can place.
 * @author UndeadScythes
 */
public class SignsCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        player.sendMessage(Color.MESSAGE + "Special signs available to you and format (lines 1-4):");
        if(player.hasPermission(Perm.SIGN_CHECKPOINT)) {
            player.sendMessage(Color.ITEM + "Checkpoint - " + Color.TEXT + "1: [checkpoint], 2-4: Anything");
        }
        if(player.hasPermission(Perm.SIGN_MINECART)) {
            player.sendMessage(Color.ITEM + "Minecart - " + Color.TEXT + "1: [minecart], 2-4: Anything");
        }
        if(player.hasPermission(Perm.SIGN_ITEM)) {
            player.sendMessage(Color.ITEM + "Item - " + Color.TEXT + "1: [item], 2: <item>, 3: <amount>, 4: Anything");
        }
        if(player.hasPermission(Perm.SIGN_PRIZE)) {
            player.sendMessage(Color.ITEM + "Prize - " + Color.TEXT + "1: [prize], 2: <item>, 3: <amount>, 4: Anything");
        }
        if(player.hasPermission(Perm.SIGN_WARP)) {
            player.sendMessage(Color.ITEM + "Warp - " + Color.TEXT + "1: [warp], 2: <warp>, 3-4: Anything");
        }
        if(player.hasPermission(Perm.SIGN_SPLEEF)) {
            player.sendMessage(Color.ITEM + "Spleef - " + Color.TEXT + "1: [spleef], 2: <region>, 3-4: Anything");
        }
    }
}
