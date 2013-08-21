package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.ListenerWrapper;
import com.undeadscythes.udsplugin.Member;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

/**
 * @author UndeadScythes
 */
public class InventoryClick extends ListenerWrapper implements Listener {
    @EventHandler
    @SuppressWarnings("deprecation")
    public void onEvent(final InventoryClickEvent event) {
        final Member shopper = PlayerUtils.getOnlinePlayer((Player)event.getWhoClicked());
        if(shopper.isShopping() && event.getSlot() == -999) {
            final ItemStack item = event.getCursor();
            event.setResult(Event.Result.DENY);
            event.getInventory().addItem(item);
            event.setCursor(new ItemStack(Material.AIR));
        }
    }
}
