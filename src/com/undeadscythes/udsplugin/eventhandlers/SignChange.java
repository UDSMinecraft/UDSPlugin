package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * When a player changes the text of a sign.
 * @author UndeadScythes
 */
public class SignChange extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final SignChangeEvent event) {
        final String line1 = event.getLine(0);
        final String line2 = event.getLine(1);
        final SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName());
        if(line1.equalsIgnoreCase("[shop]") && player.hasPermission(Perm.SHOP_SIGN)) {
            if(line2.matches(UDSPlugin.INT_REGEX)) {
                event.setLine(0, Color.SIGN + "[SHOP]");
            } else {
                event.setCancelled(true);
                player.sendMessage(Color.ERROR + "The second line must contain a number.");
            }
        } else if(line1.equalsIgnoreCase("[checkpoint]") && player.hasPermission(Perm.SIGN_CHECKPOINT)) {
            event.setLine(0, Color.SIGN + "[CHECKPOINT]");
        } else if(line1.equalsIgnoreCase("[minecart]") && player.hasPermission(Perm.SIGN_MINECART)) {
            event.setLine(0, Color.SIGN + "[MINECART]");
        } else if(line1.equalsIgnoreCase("[prize]") && player.hasPermission(Perm.SIGN_PRIZE) && findItem(line1) != null && line2.matches(UDSPlugin.INT_REGEX)) {
            event.setLine(0, Color.SIGN + "[PRIZE]");
        } else if(line1.equalsIgnoreCase("[item]") && player.hasPermission(Perm.SIGN_ITEM) && findItem(line1) != null && line2.matches(UDSPlugin.INT_REGEX)) {
            event.setLine(0, Color.SIGN + "[ITEM]");
        } else if(line1.equalsIgnoreCase("[warp]") && player.hasPermission(Perm.SIGN_WARP)) {
            event.setLine(0, Color.SIGN + "[WARP]");
        } else if(line1.equalsIgnoreCase("[spleef]") && player.hasPermission(Perm.SIGN_SPLEEF)) {
            event.setLine(0, Color.SIGN + "[SPLEEF]");
        }
    }
}
