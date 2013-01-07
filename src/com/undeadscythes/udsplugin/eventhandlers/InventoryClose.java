package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

/**
 * When a player closes an inventory.
 * @author UndeadScythes
 */
public class InventoryClose extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final InventoryCloseEvent event) {
        final SaveablePlayer shopper = UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName());
        if(shopper.isShopping()) {
            final ItemStack handItem = event.getView().getCursor();
            if(!handItem.getType().equals(Material.AIR)) {
                if(shopper.isBuying()) {
                    event.getInventory().addItem(handItem);
                    event.getView().setCursor(new ItemStack(Material.AIR));
                }
            }
            final Block block = ((BlockState)event.getInventory().getHolder()).getBlock();
            final int price = getPrice(block);
            final ItemStack[] shoppingList = shopper.getShoppingList().getContents();
            int totalDue = 0;
            for(ItemStack item : shoppingList) {
                if(item != null) {
                    totalDue += item.getAmount() * price;
                }
            }
            if(totalDue > 0) {
                if(shopper.canAfford(totalDue)) {
                    shopper.sendMessage(Color.MESSAGE + "You spent " + totalDue + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES) + ".");
                    shopper.debit(totalDue);
                    findShopOwner(block.getLocation()).credit(totalDue);
                } else {
                    shopper.sendMessage(Color.MESSAGE + "You do not have enough money to buy these items.");
                    Inventory shop = event.getInventory();
                    Inventory cart = shopper.getInventory();
                    for(ItemStack item : shoppingList) {
                        if(item != null) {
                            shop.addItem(item);
                            cart.removeItem(item);
                        }
                    }
                }
            }
            shopper.setShopping(false);
            shopper.getShoppingList().clear();
        }
    }

    private int getPrice(final Block block) {
        final Sign sign = (Sign)block.getRelative(BlockFace.UP).getState();
        if(sign.getLine(1).equals(Color.SIGN + "Shop")) {                               //
            return Integer.parseInt(sign.getLine(3).split(":")[0].replace("B ", ""));   // Update hack fix
        }                                                                               //
        return Integer.parseInt(sign.getLine(1));
    }
}
