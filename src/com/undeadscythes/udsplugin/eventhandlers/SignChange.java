package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Fired when a player finishes editing text after placing a sign.
 * 
 * @author UndeadScythes
 */
public class SignChange extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final SignChangeEvent event) {
        final String line0 = event.getLine(0);
        final String line1 = event.getLine(1);
        final String line2 = event.getLine(2);
        final SaveablePlayer player = PlayerUtils.getOnlinePlayer(event.getPlayer().getName());
        final Block block = event.getBlock();
        if(line0.equalsIgnoreCase("[shop]") && checkPerm(player, block, Perm.SHOP_SIGN)) {
            if(line1.matches(UDSPlugin.INT_REGEX)) {
                event.setLine(0, Color.SIGN + "[SHOP]");
            } else {
                event.setCancelled(true);
                player.sendError("The second line must contain a number.");
                block.breakNaturally();
            }
        } else if(line0.equalsIgnoreCase("[checkpoint]") && checkPerm(player, block, Perm.SIGN_CHECKPOINT)) {
            event.setLine(0, Color.SIGN + "[CHECKPOINT]");
        } else if(line0.equalsIgnoreCase("[minecart]") && checkPerm(player, block, Perm.SIGN_MINECART)) {
            event.setLine(0, Color.SIGN + "[MINECART]");
        } else if(line0.equalsIgnoreCase("[prize]") && checkPerm(player, block, Perm.SIGN_PRIZE)) {
            if(findItem(line1) != null && line2.matches(UDSPlugin.INT_REGEX)) {
                event.setLine(0, Color.SIGN + "[PRIZE]");
            } else {
                badFormat(player, block);
            }
        } else if(line0.equalsIgnoreCase("[item]") && checkPerm(player, block, Perm.SIGN_ITEM)) {
            if(findItem(line1) != null && line2.matches(UDSPlugin.INT_REGEX)) {
                event.setLine(0, Color.SIGN + "[ITEM]");
            } else {
                badFormat(player, block);
            }
        } else if(line0.equalsIgnoreCase("[warp]") && checkPerm(player, block, Perm.SIGN_WARP)) {
            event.setLine(0, Color.SIGN + "[WARP]");
        } else if(line0.equalsIgnoreCase("[spleef]") && checkPerm(player, block, Perm.SIGN_SPLEEF)) {
            event.setLine(0, Color.SIGN + "[SPLEEF]");
        }
    }

    private boolean checkPerm(final SaveablePlayer player, final Block block, final Perm perm) {
        if(!player.hasPermission(perm)) {
            block.breakNaturally();
            player.sendError("You do not have permission to place this sign.");
            return false;
        }
        return true;
    }

    private void badFormat(final SaveablePlayer player, final Block block) {
        block.breakNaturally();
        player.sendError("You have not written this sign correctly.");
    }
}
