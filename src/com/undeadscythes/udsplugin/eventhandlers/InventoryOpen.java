package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

/**
 * Description.
 * @author UndeadScythes
 */
public class InventoryOpen extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(InventoryOpenEvent event) {
        InventoryHolder inventoryHolder = event.getInventory().getHolder();
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
        SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName());
        if(location != null && !player.canBuildHere(location)) {
            if(player.hasPermission(Perm.BYPASS)) {
                player.sendMessage(Color.MESSAGE + "Protection bypassed.");
                return;
            }
            event.setCancelled(true);
            player.sendMessage(Color.ERROR + "You do not have access to this block.");
            return;
        }
        boolean checkDouble = true;
        if(block.getType().equals(Material.CHEST)) {
            location = block.getLocation();
            if(block.getBlock().getRelative(BlockFace.UP).getType().equals(Material.WALL_SIGN)) {
                InventoryView inventoryView = event.getView();
                Inventory shop = inventoryView.getTopInventory();
                Sign sign = (Sign)block.getBlock().getRelative(BlockFace.UP).getState();
                if(isShopSign(sign.getLines())) {
                    checkDouble = false;
                    String playerName = sign.getLine(0).replace(Color.SIGN.toString(), "");
                    ItemStack item = findItem(sign.getLine(2));
                    item.setAmount(64);
                    if("Server".equals(playerName)) {
                        for(int i = 0; i < 27; i++) {
                            shop.setItem(i, item);
                        }
                    }
                } else if(!player.canBuildHere(location)) {
                    checkDouble = false;
                    if(player.hasPermission(Perm.BYPASS)) {
                        player.sendMessage(Color.MESSAGE + "Protection bypassed.");
                        return;
                    }
                    event.setCancelled(true);
                    player.sendMessage(Color.ERROR + "You do not have access to this block.");
                }
            } else if(!player.canBuildHere(location)) {
                checkDouble = false;
                if(player.hasPermission(Perm.BYPASS)) {
                    player.sendMessage(Color.MESSAGE + "Protection bypassed.");
                    return;
                }
                event.setCancelled(true);
                player.sendMessage(Color.ERROR + "You do not have access to this block.");
            }
        }
        if(checkDouble == true && block2 != null && block2.getType().equals(Material.CHEST)) {
            location = block2.getLocation();
            if(block2.getBlock().getRelative(BlockFace.UP).getType().equals(Material.WALL_SIGN)) {
                InventoryView inventoryView = event.getView();
                Inventory shop = inventoryView.getTopInventory();
                Sign sign = (Sign)block2.getBlock().getRelative(BlockFace.UP).getState();
                if(isShopSign(sign.getLines())) {
                    String playerName = sign.getLine(0).replace(Color.SIGN.toString(), "");
                    ItemStack item = findItem(sign.getLine(2));
                    item.setAmount(64);
                    if("Server".equals(playerName)) {
                        for(int i = 0; i < 27; i++) {
                            shop.setItem(i, item);
                        }
                    }
                } else if(!player.canBuildHere(location)) {
                    if(player.hasPermission(Perm.BYPASS)) {
                        player.sendMessage(Color.MESSAGE + "Protection bypassed.");
                        return;
                    }
                    event.setCancelled(true);
                    player.sendMessage(Color.ERROR + "You do not have access to this block.");
                }
            } else if(!player.canBuildHere(location)) {
                if(player.hasPermission(Perm.BYPASS)) {
                    player.sendMessage(Color.MESSAGE + "Protection bypassed.");
                    return;
                }
                event.setCancelled(true);
                player.sendMessage(Color.ERROR + "You do not have access to this block.");
            }
        }
    }
}
