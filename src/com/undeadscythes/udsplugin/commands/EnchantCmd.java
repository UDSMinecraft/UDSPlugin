package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.enchantments.*;

/**
 * Enchant an item.
 * @author UndeadScythes
 */
public class EnchantCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        int level;
        Enchantment enchantment;
        if(args.length == 0) {
            sendPage(1, player);
        } else if(args.length == 1) {
            if(args[0].matches("[0-9][0-9]*")) {
                sendPage(Integer.parseInt(args[0]), player);
            } else {
                if((enchantment = getEnchantment(args[0])) != null) {
                    player.getItemInHand().addEnchantment(enchantment, enchantment.getMaxLevel());
                }
            }
        } else if(numArgsHelp(2) && (enchantment = getEnchantment(args[0])) != null && (level = parseInt(args[1])) != 0 && goodEnchantLevel(enchantment, level) && canEnchant(enchantment, player.getItemInHand())) {
            player.getItemInHand().addEnchantment(enchantment, level);
        }
    }

    /**
     * Sends a full page of enchantments to the player.
     * @param page Page to send.
     * @param player Player to send page to.
     */
    private void sendPage(int page, SaveablePlayer player) {
        Enchantment[] enchantments = Enchantment.values();
        int pages = (enchantments.length + 8) / 9;
        if(pages == 0) {
            player.sendMessage(Message.NO_ENCHANTMENTS);
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendMessage(Color.MESSAGE + "--- Available Enchantments " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(Enchantment enchantment : enchantments) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    player.sendMessage(Color.ITEM + "- " + enchantment.getName());
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }
}
