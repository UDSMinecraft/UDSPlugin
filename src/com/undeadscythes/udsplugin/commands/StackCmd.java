package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.inventory.*;

/**
 * Stack the items in the players inventory.
 * @author UndeadScythes
 */
public class StackCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        ItemStack[] items = player.getInventory().getContents();
        final int len = items.length;
        boolean affected = false;
        for (int i = 0; i < len; i++) {
            final ItemStack item = items[i];
            if (item != null && item.getAmount() > 0 && item.getMaxStackSize() > 1) {
                final int max = item.getMaxStackSize();
                if (item.getAmount() < max) {
                    int needed = max - item.getAmount();
                    for (int j = i + 1; j < len; j++) {
                        final ItemStack item2 = items[j];
                        if (item2 != null && item2.getAmount() > 0 && item.getMaxStackSize() > 1) {
                            final boolean a = item2.getTypeId() == item.getTypeId();
                            final boolean b = item.getDurability() == item2.getDurability();
                            final boolean c = item.getEnchantments().equals(item2.getEnchantments());
                            if (a && b && c) {
                                if (item2.getAmount() > needed) {
                                    item.setAmount(64);
                                    item2.setAmount(item2.getAmount() - needed);
                                    break;
                                }
                                items[j] = null;
                                item.setAmount(item.getAmount() + item2.getAmount());
                                needed = 64 - item.getAmount();
                                affected = true;
                            }
                        }
                    }
                }
            }
        }
        if(affected) {
            player.getInventory().setContents(items);
        }
    }
}
