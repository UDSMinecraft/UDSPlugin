package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.members.*;
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
        final Member shopper = MemberUtils.getOnlineMember((Player)event.getWhoClicked());
        if(shopper.isShopping() && event.getSlot() == -999) {
            final ItemStack item = event.getCursor();
            event.setResult(Event.Result.DENY);
            event.getInventory().addItem(item);
            event.setCursor(new ItemStack(Material.AIR));
        }
    }
}
