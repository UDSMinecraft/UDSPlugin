package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.Material;
import org.bukkit.inventory.*;

/**
 * Don your scuba gear.
 * @author UndeadScythes
 */
public class ScubaCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(player.getInventory().getHelmet() == null) {
            if(player.getItemInHand().getType() == Material.GLASS) {
                player.getInventory().setHelmet(new ItemStack(Material.GLASS));
                final ItemStack glass = player.getItemInHand().clone();
                glass.setAmount(glass.getAmount() - 1);
                player.setItemInHand(glass);
                player.sendMessage(Color.MESSAGE + "You put on your scuba gear.");
            } else {
                player.sendMessage(Color.ERROR + "You need glass in your hand to do this.");
            }
        } else if(player.getInventory().getHelmet().getType() == Material.GLASS) {
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            if(player.getInventory().addItem(new ItemStack(Material.GLASS)).isEmpty()) {
                player.sendMessage(Color.MESSAGE + "You take off your scuba gear.");
            } else {
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GLASS));
                player.sendMessage(Color.MESSAGE + "You dropped your scuba gear on the floor.");
            }
        } else {
            player.sendMessage(Color.ERROR + "You cannot do this while you have a normal helmet on.");
        }
    }
}
