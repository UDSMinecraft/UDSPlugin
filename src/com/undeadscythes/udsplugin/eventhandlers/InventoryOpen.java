package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.block.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

/**
 * Fired when a player opens an inventory window.
 * If the inventory is protected and the player does not have access and does
 * not have Perm.BYPASS the action is stopped. If the inventory is a shop then
 * the player is put in 'shopping mode'. If the inventory is the players pack
 * then nothing happens.
 * 
 * @author UndeadScythes
 */
public class InventoryOpen extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final InventoryOpenEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();
        final SaveablePlayer player = PlayerUtils.getOnlinePlayer(event.getPlayer().getName());
        if(holder instanceof DoubleChest) {
            if(isShop(((DoubleChest)holder).getLeftSide())) {
                startShopping(((Chest)((DoubleChest)holder).getLeftSide()).getLocation(), player);
            } else if(isShop(((DoubleChest)holder).getRightSide())) {
                startShopping(((Chest)((DoubleChest)holder).getRightSide()).getLocation(), player);
            } else {
                event.setCancelled(isProtected(((DoubleChest)holder).getLeftSide(), player) || isProtected(((DoubleChest)holder).getRightSide(), player));
            }
        } else if(holder instanceof Chest) {
            if(isShop(holder)) {
                startShopping(((Chest)holder).getLocation(), player);
            } else {
                event.setCancelled(isProtected(holder, player));
            }
        } else if(!(holder instanceof Player || holder instanceof Horse || holder == null)) {
            event.setCancelled(isProtected(holder, player));
        }
    }

    private void startShopping(final Location shop, final SaveablePlayer shopper) {
        if(!shopper.canBuildHere(shop)) {
            shopper.setShopping(true);
            shopper.getShoppingList().clear();
            shopper.setShop(shop);
        }
    }

    private boolean isShop(final InventoryHolder holder) {
        final BlockState block = ((BlockState)holder).getBlock().getRelative(BlockFace.UP).getState();
        if(block.getType().equals(Material.WALL_SIGN)) {
            return isShopSign(((Sign)block).getLines());
        } else {
            return false;
        }
    }

    private boolean isProtected(final InventoryHolder holder, final SaveablePlayer player) {
        if(!player.canBuildHere(((BlockState)holder).getBlock().getLocation())) {
            if(player.hasPermission(Perm.BYPASS)) {
                player.sendNormal("Protection bypassed.");
            } else {
                player.sendError("You do not have access to this block.");
                return true;
            }
        }
        return false;
    }
}
