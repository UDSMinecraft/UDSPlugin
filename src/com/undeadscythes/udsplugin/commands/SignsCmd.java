package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Get a list of special signs the player can place.
 * @author UndeadScythes
 */
public class SignsCmd extends CommandValidator {
    @Override
    public void playerExecute() {
        player.sendNormal("Special signs available to you and format (lines 1-4):");
        if(player.hasPermission(Perm.SIGN_CHECKPOINT)) {
            player.sendListItem("Checkpoint - ", "1: [checkpoint], 2-4: Anything");
        }
        if(player.hasPermission(Perm.SIGN_MINECART)) {
            player.sendListItem("Minecart - ", "1: [minecart], 2-4: Anything");
        }
        if(player.hasPermission(Perm.SIGN_ITEM)) {
            player.sendListItem("Item - ", "1: [item], 2: <item>, 3: <amount>, 4: Anything");
        }
        if(player.hasPermission(Perm.SIGN_PRIZE)) {
            player.sendListItem("Prize - ", "1: [prize], 2: <item>, 3: <amount>, 4: Anything");
        }
        if(player.hasPermission(Perm.SIGN_WARP)) {
            player.sendListItem("Warp - ", "1: [warp], 2: <warp>, 3-4: Anything");
        }
        if(player.hasPermission(Perm.SIGN_SPLEEF)) {
            player.sendListItem("Spleef - ", "1: [spleef], 2: <region>, 3-4: Anything");
        }
    }
}
