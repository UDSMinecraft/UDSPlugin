package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

/**
 * When a player clicks in an inventory window.
 * @author UndeadScythes
 */
public class InventoryClick extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final InventoryClickEvent event) {
        if(event.getInventory().getHolder() instanceof Chest) {
            final Chest chest = (Chest) event.getInventory().getHolder();
            if(chest.getBlock().getRelative(BlockFace.UP).getType() == Material.WALL_SIGN) {
                final Sign sign = (Sign) chest.getBlock().getRelative(BlockFace.UP).getState();
                if(isShopSign(sign.getLines())) {
                    final String shopOwner = sign.getLine(0).replace(Color.SIGN.toString(), "");
                    boolean serverShop = false;
                    if("Server".equals(shopOwner)) {
                        serverShop = true;
                    }
                    if(event.getWhoClicked().getName().equalsIgnoreCase(shopOwner)) {
                        return;
                    }
                    if(event.getCursor().getTypeId() == 0) {
                        final SaveablePlayer customer = UDSPlugin.getOnlinePlayers().get(((Player)event.getWhoClicked()).getName());
                        final ItemStack tradingItem = event.getCurrentItem().clone();
                        final ItemStack shopItem = findItem(sign.getLine(2));
                        if(tradingItem.getType() == shopItem.getType() && tradingItem.getData().getData() == shopItem.getData().getData()) {
                            tradingItem.setAmount(1);
                            final int rawSlot = event.getRawSlot();
                            int tradeValue;
                            boolean shopIsSelling;
                            SaveablePlayer owner = null;
                            if(!serverShop) {
                                owner = UDSPlugin.getPlayers().get(shopOwner);
                            }
                            final InventoryView inventoryView = event.getView();
                            final Inventory shop = inventoryView.getTopInventory();
                            final Inventory pack = inventoryView.getBottomInventory();
                            if(event.isShiftClick()) {
                                tradingItem.setAmount(event.getCurrentItem().getAmount());
                            } else if(event.isRightClick()) {
                                tradingItem.setAmount(event.getCurrentItem().getAmount() / 2);
                            }
                            final int buyPrice = Integer.parseInt(sign.getLine(3).split(":")[0].replace("B ", ""));
                            final int sellPrice = Integer.parseInt(sign.getLine(3).split(":")[1].replace(" S", ""));
                            if(rawSlot < 27) {
                                tradeValue = buyPrice * tradingItem.getAmount();
                                shopIsSelling = true;
                            } else {
                                tradeValue = sellPrice * tradingItem.getAmount();
                                shopIsSelling = false;
                            }
                            if(shopIsSelling) {
                                if(tradeValue == 0) {
                                    customer.sendMessage(Color.ERROR + "This shop does not sell that item.");if(customer.canAfford(tradeValue)) {
                                        final HashMap<Integer, ItemStack> overflow = pack.addItem(tradingItem.clone());
                                        int overflowAmount = 0;
                                        if(!overflow.isEmpty()) {
                                            overflowAmount = overflow.get(0).getAmount();
                                        }
                                        customer.debit(tradeValue - overflowAmount * buyPrice);
                                        if(!serverShop) {
                                            owner.credit(tradeValue - overflowAmount * buyPrice);
                                        }
                                        tradingItem.setAmount(event.getCurrentItem().getAmount() - tradingItem.getAmount() + overflowAmount);
                                        shop.setItem(rawSlot, tradingItem);
                                    } else {
                                        customer.sendMessage(Color.ERROR + "You do not have enough money to buy that.");
                                    }
                                } else {
                                    if(customer.canAfford(tradeValue)) {
                                        final HashMap<Integer, ItemStack> overflow = pack.addItem(tradingItem.clone());
                                        int overflowAmount = 0;
                                        if(!overflow.isEmpty()) {
                                            overflowAmount = overflow.get(0).getAmount();
                                        }
                                        customer.debit(tradeValue - overflowAmount * buyPrice);
                                        if(!serverShop) {
                                            owner.credit(tradeValue - overflowAmount * buyPrice);
                                        }
                                        tradingItem.setAmount(event.getCurrentItem().getAmount() - tradingItem.getAmount() + overflowAmount);
                                        shop.setItem(rawSlot, tradingItem);
                                    } else {
                                        customer.sendMessage(Color.ERROR + "You do not have enough money to buy that.");
                                    }
                                }
                            } else {
                                if(tradeValue == 0) {
                                    customer.sendMessage(Color.ERROR + "This shop does not buy that item.");
                                } else {
                                    if(serverShop || owner.canAfford(tradeValue)) {
                                        final HashMap<Integer, ItemStack> overflow = shop.addItem(tradingItem.clone());
                                        int overflowAmount = 0;
                                        if(!overflow.isEmpty() && !serverShop) {
                                            overflowAmount = overflow.get(0).getAmount();
                                        }
                                        customer.credit(tradeValue - overflowAmount * sellPrice);
                                        if(!serverShop) {
                                            owner.debit(tradeValue - overflowAmount * sellPrice);
                                        }
                                        tradingItem.setAmount(event.getCurrentItem().getAmount() - tradingItem.getAmount() + overflowAmount);
                                        pack.setItem(event.getSlot(), tradingItem);
                                    } else {
                                        customer.sendMessage(Color.ERROR + "This shop does not have enough money to buy that.");
                                    }
                                }
                            }
                        } else {
                            customer.sendMessage(Color.ERROR + "This shop does not trade that item.");
                        }
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
