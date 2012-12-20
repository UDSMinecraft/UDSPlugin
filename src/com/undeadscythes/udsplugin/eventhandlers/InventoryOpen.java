package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

/**
 * When a player opens an inventory window.
 * @author UndeadScythes
 */
public class InventoryOpen extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final InventoryOpenEvent event) {
        final String protectionBypassed = Color.MESSAGE + "Protection bypassed.";
        final String noAccess = Color.ERROR + "You do not have access to this block.";
        final InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if(inventoryHolder instanceof Player) {
            return;
        }
        if(event.getInventory().getHolder() == null) {
            return; // Anvil has no inventory holder.
        }
        BlockState block;
        BlockState block2 = null;
        if(inventoryHolder instanceof DoubleChest) {
            block = (BlockState)((DoubleChest)inventoryHolder).getLeftSide();
            block2 = (BlockState)((DoubleChest)inventoryHolder).getRightSide();
        } else {
            block = (BlockState)inventoryHolder;
        }
        Location location = null;
        if(block.getType().equals(Material.ENDER_CHEST)) {
            event.setCancelled(true);
            return;
        }
        if(block.getType().equals(Material.BREWING_STAND)) {
            location = block.getLocation();
        }
        if(block.getType().equals(Material.FURNACE)) {
            location = block.getLocation();
        }
        if(block.getType().equals(Material.DISPENSER)) {
            location = block.getLocation();
        }
        final SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName());
        if(location != null && !player.canBuildHere(location)) {
            if(player.hasPermission(Perm.BYPASS)) {
                player.sendMessage(protectionBypassed);
                return;
            }
            event.setCancelled(true);
            player.sendMessage(noAccess);
            return;
        }
        boolean checkDouble = true;
        if(block.getType().equals(Material.CHEST)) {
            location = block.getLocation();
            if(block.getBlock().getRelative(BlockFace.UP).getType().equals(Material.WALL_SIGN)) {
                final InventoryView inventoryView = event.getView();
                final Inventory shop = inventoryView.getTopInventory();
                final Sign sign = (Sign)block.getBlock().getRelative(BlockFace.UP).getState();
                if(isShopSign(sign.getLines())) {
                    checkDouble = false;
                    final String playerName = sign.getLine(0).replace(Color.SIGN.toString(), "");
                    final ItemStack item = findItem(sign.getLine(2));
                    item.setAmount(64);
                    if("Server".equals(playerName)) {
                        for(int i = 0; i < 27; i++) {
                            shop.setItem(i, item);
                        }
                    }
                } else if(!player.canBuildHere(location)) {
                    checkDouble = false;
                    if(player.hasPermission(Perm.BYPASS)) {
                        player.sendMessage(protectionBypassed);
                        return;
                    }
                    event.setCancelled(true);
                    player.sendMessage(noAccess);
                }
            } else if(!player.canBuildHere(location)) {
                checkDouble = false;
                if(player.hasPermission(Perm.BYPASS)) {
                    player.sendMessage(protectionBypassed);
                    return;
                }
                event.setCancelled(true);
                player.sendMessage(noAccess);
            }
        }
        if(checkDouble && block2 != null && block2.getType().equals(Material.CHEST)) {
            location = block2.getLocation();
            if(block2.getBlock().getRelative(BlockFace.UP).getType().equals(Material.WALL_SIGN)) {
                final InventoryView inventoryView = event.getView();
                final Inventory shop = inventoryView.getTopInventory();
                final Sign sign = (Sign)block2.getBlock().getRelative(BlockFace.UP).getState();
                if(isShopSign(sign.getLines())) {
                    final String playerName = sign.getLine(0).replace(Color.SIGN.toString(), "");
                    final ItemStack item = findItem(sign.getLine(2));
                    item.setAmount(64);
                    if("Server".equals(playerName)) {
                        for(int i = 0; i < 27; i++) {
                            shop.setItem(i, item);
                        }
                    }
                } else if(!player.canBuildHere(location)) {
                    if(player.hasPermission(Perm.BYPASS)) {
                        player.sendMessage(protectionBypassed);
                        return;
                    }
                    event.setCancelled(true);
                    player.sendMessage(noAccess);
                }
            } else if(!player.canBuildHere(location)) {
                if(player.hasPermission(Perm.BYPASS)) {
                    player.sendMessage(protectionBypassed);
                    return;
                }
                event.setCancelled(true);
                player.sendMessage(noAccess);
            }
        }
    }
}
