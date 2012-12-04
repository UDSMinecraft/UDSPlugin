package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.inventory.*;

/**
 * When a player changes the text of a sign.
 * @author UndeadScythes
 */
public class SignChange extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final SignChangeEvent event) {
        final Block block = event.getBlock();
        final String line0 = event.getLine(0);
        final String line1 = event.getLine(1);
        final String line2 = event.getLine(2);
        SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName());
        if(event.getLine(1).equalsIgnoreCase("shop")) {
            if(player.hasPermission(Perm.SHOP_SERVER) && line0.equalsIgnoreCase("server")) {
                if(isShopSign(event.getLines())) {
                    event.setLine(0, Color.SIGN + "Server");
                    event.setLine(1, Color.SIGN + "Shop");
                    event.setLine(3, "B " + event.getLine(3) + " S");
                } else {
                    event.getPlayer().sendMessage(Color.ERROR + "This sign shop is not formatted correctly. Check with /shop sign.");
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
                }
            } else if(player.hasPermission(Perm.SHOP_SIGN)) {
                if(isShopSign(event.getLines())) {
                    Location pLoc = event.getBlock().getLocation();
                    if(UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName()).isInShop(pLoc)) {
                        event.setLine(0, Color.SIGN + findShopOwner(pLoc).getName());
                        event.setLine(1, Color.SIGN + "Shop");
                        event.setLine(3, "B " + event.getLine(3) + " S");
                    } else {
                        event.getPlayer().sendMessage(Color.ERROR + "You can only place shop signs in a shop you own or work in.");
                        event.setCancelled(true);
                        block.setType(Material.AIR);
                        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
                    }
                } else {
                    event.getPlayer().sendMessage(Color.ERROR + "This sign shop is not formatted correctly. Check with /shop sign.");
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
                }
            } else {
                event.getPlayer().sendMessage(Color.ERROR + "You cannot do that here.");
                event.setCancelled(true);
                block.setType(Material.AIR);
                block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
            }
        } else if(line0.equalsIgnoreCase("[checkpoint]")) {
            if(player.hasPermission(Perm.SIGN_CHECKPOINT)) {
                event.setLine(0, Color.SIGN + "[CHECKPOINT]");
            } else {
                    event.getPlayer().sendMessage(Color.ERROR + "You cannot place checkpoint signs.");
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
            }
        } else if(line0.equalsIgnoreCase("[minecart]")) {
            if(player.hasPermission(Perm.SIGN_MINECART)) {
                event.setLine(0, Color.SIGN + "[MINECART]");
            } else {
                    event.getPlayer().sendMessage(Color.ERROR + "You cannot place minecart signs.");
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
            }
        } else if(line0.equalsIgnoreCase("[prize]")) {
            if(player.hasPermission(Perm.SIGN_PRIZE) && findItem(line1) != null && line2.matches("[0-9][0-9]*")) {
                event.setLine(0, Color.SIGN + "[PRIZE]");
            } else {
                    event.getPlayer().sendMessage(Color.ERROR + "You cannot place prize signs.");
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
            }
        } else if(line0.equalsIgnoreCase("[item]")) {
            if(player.hasPermission(Perm.SIGN_ITEM) && findItem(line1) != null && line2.matches("[0-9][0-9]*")) {
                event.setLine(0, Color.SIGN + "[ITEM]");
            } else {
                    event.getPlayer().sendMessage(Color.ERROR + "You cannot place item signs.");
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
            }
        } else if(line0.equalsIgnoreCase("[warp]")) {
            if(player.hasPermission(Perm.SIGN_WARP)) {
                event.setLine(0, Color.SIGN + "[WARP]");
            } else {
                    event.getPlayer().sendMessage(Color.ERROR + "You cannot place warp signs.");
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
            }
        } else if(line0.equalsIgnoreCase("[spleef]")) {
            if(player.hasPermission(Perm.SIGN_SPLEEF)) {
                event.setLine(0, Color.SIGN + "[SPLEEF]");
            } else {
                    event.getPlayer().sendMessage(Color.ERROR + "You cannot place spleef signs.");
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
            }
        }
    }
}
