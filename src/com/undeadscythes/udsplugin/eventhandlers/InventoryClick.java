package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

/**
 * Fired when a player clicks in an inventory window.
 * 
 * @author UndeadScythes
 */
public class InventoryClick extends ListenerWrapper implements Listener {
    @EventHandler
    @SuppressWarnings("deprecation")
    public final void onEvent(final InventoryClickEvent event) {
        final SaveablePlayer shopper = PlayerUtils.getOnlinePlayer(event.getWhoClicked().getName());
        if(shopper.isShopping() && event.getSlot() == -999) {
            final ItemStack item = event.getCursor();
            event.setResult(Event.Result.DENY);
            event.getInventory().addItem(item);
            event.setCursor(new ItemStack(Material.AIR));
        }
    }
}
